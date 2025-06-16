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

import java.util.*;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final FirebaseInitialize firebaseInitialize;

    @Override
    public void saveLog(LogDTO logDTO) {
        Firestore firestore = firebaseInitialize.getFirestore();

        Map<String, Object> log = new HashMap<>();
        log.put("deviceIdForMqtt", logDTO.getMemberIdOnDevice());
        log.put("memberIdOnDevice", logDTO.getMemberIdOnDevice());
        log.put("result", logDTO.getResult());
        log.put("timestamp", logDTO.getTimestamp());

        firestore.collection("fingerprint-door-lock").add(log);
    }

    public List<LogDTO> getLogsByDeviceId(int deviceIdForMqtt) {
        Firestore firestore = firebaseInitialize.getFirestore();
        List<LogDTO> logList = new ArrayList<>();

        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("fingerprint-door-lock")
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
