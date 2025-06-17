package com.physicalcomputing.fingerprintdoorlock.service.LogService;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.physicalcomputing.fingerprintdoorlock.domain.dto.LogDTO;
import com.physicalcomputing.fingerprintdoorlock.firestore.FirebaseInitialize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final FirebaseInitialize firebaseInitialize;

    private static final DateTimeFormatter ARDUINO_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss.SSS");

    /** 서버 기준 타임존 (필요하면 UTC 등으로 변경) */
    private static final ZoneId SERVER_ZONE = ZoneId.of("Asia/Seoul");

    @Override
    public void saveLog(LogDTO logDTO) {

        Firestore firestore = firebaseInitialize.getFirestore();

        /* 1. 문자열(또는 LocalDateTime)을 Instant로 변환 */
        Instant instant;
        Object tsRaw = logDTO.getTimestamp();

        if (tsRaw instanceof String s) {                           // 문자열일 때
            LocalDateTime ldt = LocalDateTime.parse(s, ARDUINO_FMT);
            instant = ldt.atZone(SERVER_ZONE).toInstant();
        } else if (tsRaw instanceof LocalDateTime ldt) {           // LocalDateTime일 때
            instant = ldt.atZone(SERVER_ZONE).toInstant();
        } else if (tsRaw instanceof Instant in) {                  // 이미 Instant라면
            instant = in;
        } else {
            throw new IllegalArgumentException("지원하지 않는 timestamp 타입");
        }

        /* 2. Firestore용 Timestamp 객체 생성 */
        com.google.cloud.Timestamp ts =
                com.google.cloud.Timestamp.ofTimeSecondsAndNanos(
                        instant.getEpochSecond(), instant.getNano());

        /* 3. 저장용 Map 구성 */
        Map<String, Object> log = new HashMap<>();
        log.put("deviceIdForMqtt", logDTO.getDeviceIdForMqtt());
        log.put("memberIdOnDevice", logDTO.getMemberIdOnDevice());
        log.put("result",           logDTO.getResult());
        log.put("timestamp",        ts);         // ← Firestore 네이티브 타입

        firestore.collection("fingerprint-logs").add(log);
    }

    public List<LogDTO> getLogsByDeviceId(int deviceIdForMqtt) {
        Firestore firestore = firebaseInitialize.getFirestore();
        List<LogDTO> logList = new ArrayList<>();

        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("fingerprint-logs")
                    .whereEqualTo("deviceIdForMqtt", deviceIdForMqtt)
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get();

            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                LogDTO log = new LogDTO();
                log.setDeviceIdForMqtt(Objects.requireNonNull(doc.getLong("deviceIdForMqtt")).intValue());
                log.setMemberIdOnDevice(Objects.requireNonNull(doc.getLong("memberIdOnDevice")).intValue());
                log.setResult(Boolean.TRUE.equals(doc.getBoolean("result")));
                log.setTimestamp(Objects.requireNonNull(doc.getTimestamp("timestamp")).toSqlTimestamp().toLocalDateTime());
                logList.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return logList;
    }

}
