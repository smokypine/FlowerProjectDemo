MySql 연동 상태. 다만 이 점을 지켜줄 것.

<h3> 1. application.properties 파일에서 </h3><br>

<p>spring.datasource.username=root <-------------- !!!</p>
<p>spring.datasource.password=drkim4926^ <-------- !!!!</p><br>

<p>이 두 부분을 본인 컴퓨터의 MySql에 맞는 걸로 변환시키기.</p>

<h3>2. FlowerApplication.java를 실행시키기 이전에 MySql에서</h3><br>

<p>drop schema if exists firstproject_db;</p>
<p>create schema firstproject_db;</p>
<p>use firstproject_db;</p>

<h3>이 코드를 한 번 꼭 실행해 data를 리프레시 시킬 것. 그렇지 않으면 에러가 남.</h3>
