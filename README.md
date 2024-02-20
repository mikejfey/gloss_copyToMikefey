Example calls:

Local:
```
mvn compile test -Dtest=CartTests -DdriverConfig=local_safari.json -DrunningConfiguration=LIVE -DuseLocalDrivers=true -DdownloadResults=never -DreportingEnabled=false
add also -DforkCount=0 to enable debug execution
```

Browserstack run:
```
mvn clean compile test -Dtest=CartTests -DdriverConfig=BS_Chrome.json -DrunningConfiguration=LIVE -DuseLocalDrivers=false -DproductId=33483 -DapiKey=677ff479-d53a-49ee-a0b8-421e56141a253207ef -DtestRailReportingEnabled=false -Dexecution=clientside -DdownloadResults=never
```

Run and publish to Testrail:
```
mvn clean compile test -Dtest=CartTests -DdriverConfig=BS_Chrome.json -DrunningConfiguration=LIVE -DuseLocalDrivers=false -DtestRailProjectId=109 -DproductId=33483 -DapiKey=677ff479-d53a-49ee-a0b8-421e56141a253207ef -DtestRailReportingEnabled=true -DaddAllTestsToPlan=false -DtestRailRunName=MockRun -DtestRailPlanName=Regression.Glossier -DtestRailRunNameAllowReuse=true -DtestRailSuiteId=50375 -Dexecution=clientside -DdownloadResults=never
```

Generate Allure HTML report:
```
mvn allure:serve
```