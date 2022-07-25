## Branches

_master_ - main branch

_feature/**_ - branch with features that is not a test, fix or devops infra

_test/**_ - branch with new test

_fix/**_ - fix the issue with the test or framework

_infra/**_ - DevOps activities

## Linter Rules
Service has a rule set of the __checkstyle__ and __pmd__ maven plugins to check the code developed. To run linter check use the command:
* Windows -> `mvnw.cmd clean validate`
* macOS/Linux -> `./mvnw clean validate`

Ruleset files are located in the _config_ folder at the project's root.

More about <a href="https://pmd.github.io/">PMD</a> and <a href="https://checkstyle.sourceforge.io/">Checkstyle</a>

## Pull Request rules

* At least that one approval
* Success pipeline
* No open threads
