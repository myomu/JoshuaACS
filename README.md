
# Joshua ACS
>Joshua Attendance Check System(ACS) 는 출석 체크를 관리하는 웹 어플리케이션으로 1인 개발로 진행된 토이 프로젝트입니다. 제작기간은 2023년 5월 2일 부터 시작하여 현재까지 진행중 입니다.





## 프로젝트의 목적

>한 집단에 속해 활동을 하다보면 다양한 이유로 해당 집단의 출석 인원을 파악해야 할 때가 있습니다. 이러한 출석을 서면으로 관리하지않고 웹 상에서 간편히 저장하고 관리할 수 있도록 만든 출석 체크 웹 어플리케이션 입니다.





## 기술 스택

- JAVA 17
- Spring Framework
- Spring Boot 3.0.6
- Git
- AWS
    - EC2 - Ubuntu 22.04
    - RDS - MySQL
    - Route 53 (Https 통신)
    - S3 (Img file 저장 & Git Actions 사용)
    - CodeDeploy (Git Actions 사용)





## 외부 라이브러리 및 사용 Template

- 외부 라이브러리
    - Simple-DataTables https://github.com/fiduswriter/Simple-DataTables/wiki
- 화면 구성 Template
    - https://startbootstrap.com/template/sb-admin (html, css 사용)





## AWS Architecture

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/JoshuaACS+AWS+%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png" />



## ERD

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/JoshuaACS+ERD.png" />





## 프로젝트 개요

### 1. 로그인

>로그인에 성공하면 스프링에서 제공하는 session을 만들어 클라이언트에게 전송합니다. 또한 로그인 하지않고 다른 화면의 URL에 접근하는 것을 막기 위해 스프링 인터셉터를 통해 로그인화면을 제외한 곳으로 이동하는 것을 막습니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%EB%A1%9C%EA%B7%B8%EC%9D%B8+%ED%99%94%EB%A9%B4.png"/>





### 2. 메인

>Joshua ACS 에 대한 간략한 소개문을 담고 있습니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%EB%A9%94%EC%9D%B8+%ED%99%94%EB%A9%B4.png"/>





### 3. 출석체크

>메인 기능인 출석 체크 화면입니다. 수동 날짜 입력을 통해 출석하는 날짜를 지정할 수 있으며 수동 입력을 사용하지 않을 시 현재 날짜를 기준으로 저장합니다. 표시되는 회원들은 각자 소속된 조별로 모여서 보여집니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%EC%B6%9C%EC%84%9D+%EC%B2%B4%ED%81%AC+%ED%99%94%EB%A9%B4.png"/>



>출석 체크한 것을 테이블 형식으로 보여주는 화면입니다. 저장한 날짜와 체크된 회원들, 총인원 수를 보여주고 관리 column의 버튼을 클릭하여 수정과 삭제가 가능합니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%EC%B6%9C%EC%84%9D+%EC%B2%B4%ED%81%AC+%EB%AA%A9%EB%A1%9D+%ED%99%94%EB%A9%B4.png"/>





### 4. 회원 관리

>회원들의 정보를 테이블 형식으로 보여주는 화면입니다. 회원의 이름, 나이, 성별, 조 이름, 출석률을 보여주고 관리 column의 버튼을 클릭하여 수정과 삭제가 가능합니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%ED%9A%8C%EC%9B%90+%EB%AA%A9%EB%A1%9D+%ED%99%94%EB%A9%B4.png"/>



>회원 추가 화면입니다. 이름, 나이, 성별, 조이름을 선택하여 저장합니다. 조를 추가한다면 추가된 조가 조 선택에 표시됩니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%ED%9A%8C%EC%9B%90+%EC%B6%94%EA%B0%80+%ED%99%94%EB%A9%B4.png"/>





### 5. 조 관리

>조들의 정보를 테이블 형식으로 보여주는 화면입니다. 조의 ID, 조 이름을 보여주고 관리 column의 버튼을 클릭하여 수정과 삭제가 가능합니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%EC%A1%B0+%EB%AA%A9%EB%A1%9D+%ED%99%94%EB%A9%B4.png"/>



>조 추가 화면입니다. 조의 이름을 넣고 저장합니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%EC%A1%B0+%EC%B6%94%EA%B0%80+%ED%99%94%EB%A9%B4.png"/>





### 6. 회의록

>회의록 화면입니다. 회의록 추가 버튼을 클릭하여 날짜와 회의록 이미지 파일을 선택하여 저장합니다. 이미지 파일은 AWS S3에 저장되고 해당 이미지의 주소를 DB에 저장합니다. DB에 저장된 주소를 통해 이미지를 가져옵니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%ED%9A%8C%EC%9D%98%EB%A1%9D+%ED%99%94%EB%A9%B4.png"/>



>이미지의 아래쪽 trash 아이콘을 통해 삭제를 할 수 있습니다. 또한 이미지를 클릭하면 모달창으로 이미지가 표시되며 다운로드 버튼을 클릭하여 이미지 파일을 다운받을 수 있습니다.

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%ED%9A%8C%EC%9D%98%EB%A1%9D+%EC%A0%80%EC%9E%A5.png"/>

<img src="https://joshuaacsbucket.s3.ap-northeast-2.amazonaws.com/readme/%ED%9A%8C%EC%9D%98%EB%A1%9D+%ED%99%95%EB%8C%80+%ED%99%94%EB%A9%B4.png"/>




## 총평
이 프로젝트는 SpringFramework로 구현해보는 첫 웹 어플리케이션 입니다. 처음인만큼 배워야할 것도 많고 오류가 나면 무엇이 원인인지 찾기가 힘들었던 것 같습니다. 그러나 이런 과정을 통해 Network 통신, Spring, AWS, Git Actions 등을 구현해보며 웹 어플리케이션이 동작하는 방식과 배포과정을 많이 배웠던 프로젝트라고 생각합니다.  앞으로 실제 사용을 해보면서 시간이 된다면 출석률을 그래프로 표현한다던가 기능을 추가하거나 사용성에 문제가 없는지 파악하고 업데이트를 해나갈 예정입니다.