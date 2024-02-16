Example calls:

Local:
```
mvn compile test -Dtest=CartTests -DdriverConfig=local_safari.json -DrunningConfiguration=LIVE -DuseLocalDrivers=true -DdownloadResults=never -DreportingEnabled=false
add also -Dmaven.surefire.debug to enable debug execution
```

Browserstack:
```
mvn compile test -Dtest=CartTests -DdriverConfig=BS_Safari.json -DrunningConfiguration=LIVE -DuseLocalDrivers=false -DtestRailProjectId=109 -DproductId=33227 -DapiKey=Azhnz/vkv0mBgQ7MFawL-p/5emvCzNj8jbzOojxS0
```

Run and publish to Testrail:
```
mvn clean compile test -Dtest=CartTests -DdriverConfig=BS_Safari.json -DrunningConfiguration=LIVE -DuseLocalDrivers=false -DtestRailProjectId=109 -DproductId=33227 -DapiKey=Azhnz/vkv0mBgQ7MFawL-p/5emvCzNj8jbzOojxS0 -DtestRailReportingEnabled=true -DaddAllTestsToPlan=false -DtestRailRunName=MockRun -DtestRailPlanName=Regression.Glossier -Dbrowser=Safari -DtestRailRunNameAllowReuse=true -DtestRailSuiteId=39698
```

Generate Allure HTML report:
```
mvn allure:serve
```