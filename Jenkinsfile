pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }

    stages {
        stage('Git Checkout') {
            steps {
                git changelog: false, poll: false, url: 'https://github.com/septianrezaandrianto/rnd-spring-boot-3.git/'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat 'mvn clean package'
                    bat ''' mvn clean verify sonar:sonar -Dsonar.projectKey=rnd-springboot-3.0 -Dsonar.projectName='rnd-springboot-3.0' -Dsonar.host.url=http://localhost:9000 '''
                }
            }
        }
        stage("Quality Gate") {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Build Image') {
            steps {
                 bat 'docker build -t septianreza/rnd-springboot-3.0 .'
            }
        }

        stage('Deploy Image') {
            steps {
                bat 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }

        stage('Push') {
            steps {
                bat 'docker push lloydmatereke/jenkins-docker-hub'
            }
        }
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}