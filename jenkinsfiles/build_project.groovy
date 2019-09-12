#!groovy
@Library('gb-libs') _
ciGradleBuild {
    BRANCH = "master"
    REPO = "git@github.com:gbprojectbr/<APP_NAME>.git"
    SONAR_PROPERTIES = 'project.settings=sonar.properties'
    GRADLE_TASKS = "<YOUR_GRADLE_TASK>"
    APPNAME = '<APP_NAME>'
    TEAM_NAME = 'Plataforma'
    REPOCONF = '<APP_NAME>'
    ROLE = '<APP_ROLE>'
    PORT  ='8080'
    DOMAIN = 'guiabolso.in'
    CLUSTER = 'guiabolso'
    ALB_TYPE = true
    DEPLOY_DELIVERY = 'Delivery'
    TIME_RANGE_TO_TEST = '300'
    MAX_AVG_ERROR  = '0.5'
}
