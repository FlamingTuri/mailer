# mailer

Microservice used to automate email send. At the moment it supports only Gmail.


## Requirements

- java 11
- a gmail account
- a google cloud project


## Setup

- create google cloud project [https://console.cloud.google.com/welcome](https://console.cloud.google.com/welcome)
- enable gmail API [https://console.cloud.google.com/apis/enableflow?apiid=gmail.googleapis.com](https://console.cloud.google.com/welcome)
- generate `credentials.json` file and put it into `$HOME/.mailer/gmail`
  - https://console.cloud.google.com/apis/credentials
  - Create Credentials > OAuth client ID
  - Configure Consent Screen (select "external", after confirmation users will not have usage limitations)
  - Application type > Desktop app
- run mailer application `java -jar mailer-$VERSION-runner.jar`
- click the link displayed in the log and accept ([https://accounts.google.com/o/oauth2/auth?***](https://console.cloud.google.com/welcome))


The microservice will listen on port 5000. Define `quarkus.http.port` to override it:
```
java -jar -Dquarkus.http.port=5555 mailer-$VERSION-runner.jar
```


## Development

Run the application with:

```
./gradlew run
```

Generate application jar:
```
./gradlew build -Dquarkus.package.type=uber-jar
```

Generated file will be located at `build/mailer-$VERSION-runner.jar`


## Contributing

Contributions, suggestions, issues and feature requests are welcome!


## License

This utility is distributed under the [MIT license](LICENSE).
