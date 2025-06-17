# 🔧 Fingerprint Door Lock - Code Architecture

본 프로젝트는 **ESP32 ↔ Spring 서버 ↔ Firebase** 간의 연동을 통해 지문 기반 출입 시스템을 구현합니다. 전체 코드의 흐름은 다음과 같이 구성됩니다.

---

## 🧭 전체 흐름 요약

1. **기기 등록**
   - ESP32 (등록기) SoftAP 모드 → HTML 페이지 제공
   - 닉네임, Wi-Fi 비밀번호 입력 → UART로 전송
2. **ESP32 (서버 연결용)**
   - UART 수신 → MAC 주소 포함 정보 서버 전송
   - 이후 MQTT 연결 및 지문 이벤트 감지
3. **Spring 서버**
   - MQTT 연결 (MqttConfig / MqttService)
   - Firestore Trigger 수신 → 위험 판단 후 이메일 전송
4. **Firebase Firestore**
   - `logs` 컬렉션 자동 저장
   - 실패 로그 3건 연속 시 서버로 위험 통지

---

## 📁 주요 코드 구성

### 🔌 ESP32 등록기

- `WiFi.softAP()`: SoftAP 설정
- `AsyncWebServer`: HTML 폼 제공
- `Serial.write()`: 입력 정보 UART 송신

### 📲 ESP32 서버 연결기

- `Serial.read()`: UART 수신
- `WiFi.begin()`: Wi-Fi 연결
- `HTTPClient`: Spring에 정보 전송 (MAC, nickname, password)
- `PubSubClient`: MQTT subscribe (토픽: `fingerprint/{deviceId}`)

---

### 🌐 Spring Boot

- `MqttConfig`: MQTT 클라이언트 설정
- `MqttService`: 메시지 발행 (QoS 1)
- `FirebaseController`: Firebase Trigger POST 수신
- `MailService`: 위험 시 이메일 전송
- `MemberController`: 기기 등록, MQTT 연결 처리

---

### 🔥 Firebase Function

```js
exports.onLogCreated = functions.firestore
  .document("logs/{logId}")
  .onCreate(async (snap, context) => {
    // 실패 로그 3건 이상 시 서버에 HTTP 요청
  });
