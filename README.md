# LEADME - 리드미
천편일률적인 여행은 지겹다. 유명 관광지부터 숨은 로컬 맛집까지!
가이드와 여행자 매칭 서비스 리드미입니다.
<br/>
<br/>
<br/>

## Technologies
[![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white)](https://github.com/samisnotthree/LEADME)
[![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white)](https://github.com/samisnotthree/LEADME)
[![SpringDataJPA](https://img.shields.io/badge/SpringDataJPA-6DB33F?style=flat-square&logoColor=white)](https://github.com/samisnotthree/LEADME)
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=Hibernate&logoColor=white)](https://github.com/samisnotthree/LEADME)
[![Querydsl](https://img.shields.io/badge/Querydsl-6DB33F?style=flat-square&logoColor=white)](https://github.com/samisnotthree/LEADME)
[![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=flat-square&logo=JUnit5&logoColor=white)](https://github.com/samisnotthree/LEADME)  
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white)](https://github.com/samisnotthree/LEADME)
[![AmazonEC2](https://img.shields.io/badge/AmazonEC2-FF9900?style=flat-square&logo=AmazonEC2&logoColor=white)](https://github.com/samisnotthree/LEADME)
[![AmazonRDS](https://img.shields.io/badge/AmazonRDS-527FFF?style=flat-square&logo=AmazonAWS&logoColor=white)](https://github.com/samisnotthree/LEADME)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=Thymeleaf&logoColor=white)](https://github.com/samisnotthree/LEADME)
<br/>
<br/>
<br/>
## ERD
![image](https://user-images.githubusercontent.com/85722378/169803885-45021b3c-d5b4-4459-8f8f-f94887b178ef.png)
<br/>
<br/>
<br/>
## Test
<img width="942" alt="image" src="https://user-images.githubusercontent.com/85722378/178270814-ab8500e1-bce6-413e-93fc-1d9b692488ff.png">

- 회귀 방지, 리팩토링 내성, 유지보수성과 테스트 커버리지를 고려한 테스트 코드 작성
- FIRST 규칙을 지킨 테스트 코드 작성
<br/>
<br/>

## 기술을 사용한 이유

### Querydsl
- Spring 환경에서 JPA를 사용하기 위해선 JPQL을 작성하게 됩니다. Spring Data JPA가 기본적으로 제공해주는 CRUD 메서드 및 쿼리 메서드 기능을 사용하더라도, 원하는 조건의 데이터를 수집하기 위해서는 필연적으로 JPQL을 작성하게 됩니다. 간단한 로직을 작성하는 데 큰 문제는 없으나 복잡한 로직의 경우 개행이 포함된 쿼리 문자열이 길어집니다. 이때 JPQL은 문법 오류가 나더라도 컴파일 시점에는 알 수 없고 런타임 시점에 에러가 발생합니다.
- 이런 문제를 해결하기 위해 Querydsl을 사용했습니다. Querydsl 도입으로 다음과 같은 이점을 얻었습니다.
  - 문자열이 아닌 코드를 사용함으로써 컴파일 시점에 문법 오류를 확인할 수 있다.
  - 자동 완성 등 IDE의 도움을 받아 편리하게 작성할 수 있다.
  - 동적쿼리 작성이 매우 편리해진다.
  - 조건, 중복 내용 등을 메서드 추출하고 재사용하여 객체지향적으로 코드를 작성할 수 있다.

<!-- ### 의미있는 이름과 함수 back 참조-->

### Setter 및 Builder

### 연관관계 설정(CQRS) 정리하기
- Entity와 연관관계를 설정하면서 이런저런 고민함 수시로 수정함 JPA 공부를 하며 도움을 많이 받았던 두 유명 개발자
김영한님은 불필요한 연관관계(특히 양방향)을 최소화 하되 연관관계 자체는 적극적으로 사용하라.
최범균님은 실무에서 연관관계는 거의 사용하지 않는다. CQRS를 도입해서 조회 로직과 저장? 로직을 분리하고 연관관계는 어쩔수 없는 경우 아니면 사용하지 마라.

### Git commit convention
