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

- 총 83개의 테스트 코드
- 회귀 방지, 리팩토링 내성, 유지보수성과 테스트 커버리지를 고려한 테스트 코드 작성
- FIRST 규칙을 지킨 테스트 코드 작성

<br/>
<br/>
<br/>

## Setter와 Builder
<img width="400" alt="image" src="https://user-images.githubusercontent.com/85722378/178982006-73c4442a-bc71-45f4-9fc5-39b29c7aca21.png">
<img width="303" alt="image" src="https://user-images.githubusercontent.com/85722378/178997966-239f8c0e-5c38-4e77-b425-f122b7ef5b34.png">

- 엔티티에서 Setter를 사용하지 않았습니다.
  - Setter를 무분별하게 남용하다 보면 여기저기서 객체의 값을 변경할 수 있으므로 객체의 일관성을 보장할 수 없습니다.
  - Setter는 그 의도를 파악하기 까다롭습니다.

<br/>
<br/>

<img width="694" alt="image" src="https://user-images.githubusercontent.com/85722378/178982409-931f418f-4133-4ca7-ad99-d1832b3fc315.png">

- 생성자와 자바빈즈 패턴의 단점을 해소하기 위해 Builder 패턴을 사용하였습니다.
  - 생성자에는 제약이 하나 있는데, 선택적 매개변수가 많을 경우에 대응이 어렵습니다.  
  - 예를들어, 받아오는 매개변수에 따라 계속해서 생성되는 생성자의 코드를 보았을때 매개변수의 개수에 따라 호출되는 생성자를 짐작하기가 매우 혼잡해집니다.  
  - 또는 생성자 호출을 위해서 설정하길 원하지않는 매개변수의 값까지 지정해줘야하는 불편함이 있습니다.  
  - 한 두개 정도는 괜찮을 수 있겠지만, 매개변수의 수가 늘어나게되면 걷잡을 수 없을 정도가 됩니다.

<br/>
<br/>
<br/>

## Querydsl
### 사용 이유
- Spring 환경에서 JPA를 사용하기 위해선 JPQL을 작성하게 됩니다. Spring Data JPA가 기본적으로 제공해주는 CRUD 메서드 및 쿼리 메서드 기능을 사용하더라도, 원하는 조건의 데이터를 수집하기 위해서는 필연적으로 JPQL을 작성하게 됩니다. 간단한 로직을 작성하는 데 큰 문제는 없으나 복잡한 로직의 경우 개행이 다수 포함되고 쿼리 문자열이 길어집니다. 이때 JPQL은 문법 오류가 나더라도 컴파일 시점에는 알 수 없고 런타임 시점에 에러가 발생합니다.
- 이런 문제를 해결하기 위해 Querydsl을 사용했습니다. Querydsl 도입으로 다음과 같은 이점을 얻었습니다.
  - 문자열이 아닌 코드를 사용함으로써 컴파일 시점에 문법 오류를 확인할 수 있습니다.
  - 자동 완성 등 IDE의 도움을 받아 편리하게 작성할 수 있습니다.
  - 동적쿼리 작성이 매우 편리해집니다.
  - 조건, 중복 내용 등을 메서드 추출하고 재사용하여 객체지향적으로 코드를 작성할 수 있습니다.

<br/>

### fetchResults(), fetchCount() deprecated

<img width="257" alt="image" src="https://user-images.githubusercontent.com/85722378/179007472-eab1f77e-6f9c-4c4c-82f4-98c76a077359.png">
<img width="608" alt="image" src="https://user-images.githubusercontent.com/85722378/179008797-53ce1308-b264-498f-904b-71c3635ab823.png">

- 예전부터 정확한 결과를 보장하지 못하던 fetchResults 메서드와 fetchCount 메서드가 공식적으로 deprecated 되었습니다.
  - count 쿼리를 따로 작성하여 해결하였습니다.
<!-- ### Cross Join 대비 -> join을 명시적으로 함. -->

<br/>
<br/>
<br/>

## 조회 전용 DTO
- 수정을 위한 DTO와 조회를 위한 DTO를 따로 생성하였습니다.
- 엔티티에 수정이 필요한 경우가 아니면 엔티티를 직접 사용하지 않는 게 좋습니다.
- 여러 테이블의 데이터를 동시에 조회해올 경우 FK에 들어갈 ID만 조회하는 등 필요한 데이터만 조회하는 전용 DTO를 따로 생성합니다.

<br/>
<br/>
<br/>

## 연관관계 설정(+ CQRS 패턴)
**※ 아래 내용은 제 주관이 반영되어 왜곡된 해석일 확률이 매우 높습니다.**
```
JPA에서의 객체 연관관계는 테이블 연관관계보다 객체지향적이며
이는 객체지향적으로 개발하는 개발자 입장에서 더 직관적입니다. 
불필요한 연관관계를 최소화 하되, 연관관계 자체는 적극적으로 활용하면 좋습니다.
```

```
개인적으로 상태를 변경하는 기능과 조회하는 기능을 구분해서 모델을 만들려고 노력하는데요.(CQRS 패턴)
상태를 변경하는 기능에서 주로 JPA를 사용합니다.

상태 변경 기능을 설계할 때 개념적으로 하나인 단위로 엔티티를 구성하고(예를 들어, 회원, 상품, 설문 등)
이 단위로 모델을 생성하거나 상태를 변경하는 기능을 구현합니다.
그러다 보니 개념적으로 하나인 엔티티 간에 연관이 필요하지 않고
또 엔티티 간 연관을 사용하면 복잡해지는 단점도 있어
어쩔 수 없는 경우가 아니면 상태 변경 기능을 구현할 때 엔티티 간 연관은 사용하지 않습니다.
```
위는 제가 JPA를 공부하면서 큰 도움을 받은 두 개발자분들께서 연관관계 관련하여 말씀하신 내용들입니다.  
두분은 JPA 관련 책도 쓰실 정도로 이해가 깊은 분들이라 두 내용 다 틀린 내용일리는 없습니다.  
하지만 제가 해석하기에는 두 내용이 서로 충돌하고 있다고 느껴졌습니다.  
그래서 어떤 환경의 차이가 있길래 이런 다른 선택을 하게 될 수 있었는지 알아보았습니다.
<!--
- 첫째로 규모의 차이가 있었습니다. 일반적으로 CQRS 패턴은 어느정도 규모가 있을 때 적용하는 패턴입니다. 위는 강의~ 실제로 실무에선 cqrs를 도입했고~ 아마 거기선 연관관계가 더 소극적으로 쓰였을것이다?
- 성향차이? jpa를 공격적으로 깊게 쓰는 회사가 있고, 어느정도장점만 조금 쓰고 덜 실용적인 상황에선 jpa를 과감하게 안쓰는 회사가 있다.
- 
-->

<br/>
<br/>
<br/>
<!-- ## 공통 로직 추상화 및 도메인 특화 언어(DSL) -->

## 경계 조건 캡슐화
if문의 조건으로 사용되는 코드가 복잡한 경우 함수로 만들어 가독성 좋게 수정하였습니다.
```java
public Long addOrder(Orders order) {
    ...

    // 기존 코드
    if (foundOrders.size() < foundOrders.get(0).getProgDaily().getProg().getMaxMember()) {
        ...
    }

    // 수정한 코드
    if (!validateOrder(order)) {
        ...
    }

    ...
}
```

<br/>
<br/>
<br/>

## Git commit convention
<img width="281" alt="image" src="https://user-images.githubusercontent.com/85722378/179015713-04f43f34-e604-45bd-9960-054e35a178e7.png">

프로젝트 진행 중반까지만 해도 git commit 메시지를 정해진 약속 없이 수정된 내용을 적어왔습니다.  
하지만 협업 상황에서 git commit 메시지 컨벤션을 지키는 것은 매우 중요하다고 생각이 되어 이를 지키며 메시지를 남겼습니다.
  - body, footer 등이나 순번 등을 추가하여 더 디테일 하게 작성할 필요가 있어보입니다.
