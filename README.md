# Magnificent service monitor

This application checks Magnificent service frequently - at least several times a minute.
It indicates how healthy the Magnificent service has been, over the last little while.
It can also tell our system administrator if Magnificent has not been responding at all.

System administrator can see the result of monitoring in the next file: **./log/magnificent.log**
Application writes one log entry per each health check with the appropriate log level (Healthy: INFO, 
Unhealthy: WARN, Unavailable: ERROR).

### Build

You can build this application using the next command:
<p><strong>mvn clean package</strong></p>

As a result of the program execution, an executable jar will be generated:
<p><strong>target/magnificent-service-monitor-VERSION-jar-with-dependencies.jar</strong></p>

### Run

You can run this application using the next command:

java -jar magnificent-service-monitor-1.0-SNAPSHOT-jar-with-dependencies.jar 

Or from your favourite IDE (just start **main** method from the **code.codechallenge.Main** class). 

### Configuration properties

The user is able to configure the service under monitoring (below you can see configuration properties and default values).
monitoring.server.schedule.fixed.rate - This property is used to configure how frequently health check should be performed.
monitoring.server.connection.timeout - This property is used to configure default connection timeout to the server.

monitoring.server.url=http://localhost
monitoring.server.port=12345
monitoring.server.schedule.fixed.rate=10000
monitoring.server.connection.timeout=5000

### Starting test server

Install Python. Python2 and Python3 both work.
pip install twisted
python server.py
Okay, now you're running magnificent!
Visit http://localhost:12345 in a web browser or something.
It should throw a verbose error, or return "Magnificent!".
