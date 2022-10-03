# CleanArchitectureSearchMovie

## 클린 아키텍처를 적용한 영화 검색 앱 입니다.

### 1. 프로젝트 구조

- presentation layer (mvvm)
    - ui elements
    - model
    - mapper
    - stateholder(viewmodel)
- domain layer
    - usecase
    - model
    - repository
- data layer
    - datasource
    - repositoryImpl
    - model
    - mapper
- remote layer
    - datasourceImpl
    - service
    - model
    - mapper
- cache layer
    - datasourceImpl
    - db
    - model
    - mapper

### 2. 기능
- 영화 찾기
- 영화 찜하기
- 찜한 영화 목록
- 영화 상세페이지
- 소셜 로그인 (예정)
- 지도 기반 주변 영화관 (예정)

### 3. 라이브러리
- coroutine
- retrofit
- okhttp
- gson
- paging
- coil
- room
- hilt
- navigation component

