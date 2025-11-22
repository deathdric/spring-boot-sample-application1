# Project 'application1'

This is a simple spring-boot based project used for interacting
with several infrastructure components, especially the database.

Credentials (database) can be optionally fetched from conjur.

## Disclaimer

There are probably lots of bad coding practices done in this project.
Please be lenient : the purpose it not to show how to code a spring
boot application nor how to perform software craftsmanship. It's just
to have a simple running application for further tests ;)

I decline all responsibility if you copy/paste anything into your
production code.

## How to run

First, you need java 21, have your `JAVA_HOME` set to its installation path,
and make sure that its cacerts file contains the root (and potentially intermediate)
authorities used to sign the certificates of any services you will have to connect to
(e.g. conjur).

Make the necessary modifications to `src/main/resources/application-sample.yml`
in order to match your local setup (database parameters for example).

Then before running the application, you need to set the spring active profiles
and environment variables.


|           Scenario            | Active Profiles | Needed Environment variables            |
|:-----------------------------:|:---------------:|:----------------------------------------|
|       No secret manager       |     sample      |                                         |
| Use conjur as secrets manager |  sample,conjur  | CONJUR_AUTHN_LOGIN,CONJUR_AUTHN_API_KEY |

If you want to launch the application from the command line, you can use the following :

```
./gradlew bootRun --args="--spring.profiles.active=sample,conjur"
```
(note : replace `./gradlew` with `gradlew` if you are on Windows, and adapt the list of active profiles
to your use case).


You can check whether the API is working by going to the following URL using your favorite HTTP
client (curl, postman, browser, ...) :

```
http://localhost:8080/api/products
```

## About the conjur setup

Explaining the full setup has not its place in this specific repository, however I still need to
explain from where the values are set in the configuration files.

I put it place a hierarchical policy setup containing the application name (`application1`)
and the environment trigram (`dev`). Then I created one policy per account.

```
myuser@myhost:~$ conjur list | grep dev
  "deathshadow:policy:app/application1/dev",
  "deathshadow:policy:app/application1/dev/db-adm",
  "deathshadow:policy:app/application1/dev/db-rw",
  "deathshadow:variable:app/application1/dev/db-adm/info",
  "deathshadow:variable:app/application1/dev/db-adm/login",
  "deathshadow:variable:app/application1/dev/db-adm/password",
  "deathshadow:variable:app/application1/dev/db-rw/info",
  "deathshadow:variable:app/application1/dev/db-rw/login",
  "deathshadow:variable:app/application1/dev/db-rw/password",
```

In theory, I could have put both passwords as variables in the same policy, but that's when
I discovered a limitation of conjur spring integration : you can't have more than one occurrence of
`@ConjurPropertySource` which references the same policy name.

Note that I could have retrieved the database login from conjur too, but I was too lazy to do that (you can
do it as an exercise :) ).

