MySql 연동 상태. 다만 이 점을 지켜줄 것.

<h3> 1. application.properties 파일에서 </h3><br>

<p>spring.datasource.username=root <-------------- !!!</p>
<p>spring.datasource.password=drkim4926^ <-------- !!!!</p><br>

<p>이 두 부분을 본인 컴퓨터의 MySql에 맞는 걸로 변환시키기.</p><br><br>

<h3>MySQL에서 아래 코드를 한 번만 기동시키기</h3>
<p>drop schema if exists flower;</p>
<p>create schema flower;</p>
<p>use flower;</p>
