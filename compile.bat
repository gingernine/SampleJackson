javac -d WebContent/WEB-INF/classes -cp C:/pleiades/tomcat/9/lib/* ^
src/sample/jackson/ReadTreeMain.java ^
src/sample/jackson/DecodeMain.java ^
src/sample/jackson/EncodeMain.java ^
src/sample/jackson/Hoge.java ^
src/sample/jackson/Info.java ^
src/sample/jackson/JsonConverter.java ^
src/sample/jackson/SampleWebSocket.java

rem start Apache Tomcat
C:\pleiades\tomcat\9\bin\startup.bat

rem java -cp WebContent/classes;C:/pleiades/tomcat/9/lib/* sample.jackson.ReadTreeMain