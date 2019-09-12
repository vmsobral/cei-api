#!groovy
@Library('gb-libs') _
ciGradlePullRequestValidator {
    REPO = "git@github.com:gbprojectbr/<APP_NAME>.git"
    REPOCONF = "<APP_NAME>"
    SONAR_PROPERTIES = 'project.settings=sonar.properties'
    GRADLE_TASKS = "<YOUR_GRADLE_TASK>"
}