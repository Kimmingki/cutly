# ✨ Cutly - 간편한 URL 단축 서비스
📌 **공식 웹사이트:** [🔗 cutly.kr](https://cutly.kr)

![Cutly Preview](resources/image/img.png)

## 🚀 소개
**Cutly**는 길고 복잡한 URL을 짧고 간편하게 변환해주는 **URL 단축 서비스**입니다.  
QR 코드 생성, 링크 분석 기능까지 지원하여 **빠르고 효과적인 링크 공유**를 도와줍니다.

---

## 🎯 기능
- ✅ **URL 단축:** 긴 URL을 짧고 간편한 링크로 변환
- ✅ **QR 코드 생성:** 단축된 URL의 QR 코드 자동 생성
- ✅ **URL 통계 제공:** 클릭 횟수, 사용량 분석 기능 제공 (추후 지원 예정)
- ✅ **만료 기능:** 설정된 시간이 지나면 자동으로 삭제
- ✅ **빠른 접근:** 간단한 UI와 서버 사이드 렌더링(SSR) 적용으로 빠른 로딩

---

## 🛠️ 기술 스택
| 분야        | 기술 스택                   |
|------------|-----------------------------|
| **Backend**  | Java 17, Spring Boot, JPA   |
| **Frontend** | Thymeleaf, jQuery, Bootstrap |
| **Database** | PostgreSQL, Redis (캐싱)    |
| **DevOps**   | Docker, GitHub Actions, AWS Lightsail |

---

## 🏗️ 프로젝트 구조
```bash
cutly/
|-- src/main/java/sideproject/cutly/
|   |-- controller/         # URL 단축 & 조회 API
|   |-- service/            # URL 관리 서비스
|   |-- repository/         # JPA Repository
|   |-- config/             # DB & Redis 설정
|-- resources/static/       # CSS, JS, Favicon
|-- resources/templates/    # Thymeleaf 템플릿 (SSR)
|-- Dockerfile              # Docker image build
|-- .env                    # 환경 변수 설정 파일
```

---