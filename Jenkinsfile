pipeline {
    agent any
    tools {
        maven 'jenkins-maven'
    }

    stages {
        stage('Git Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/septianrezaandrianto/rnd-spring-boot-3']])
                bat 'mvn clean install'
                echo 'Git Checkout Completed'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('SonarQube') {
                        try {
                            bat 'mvn clean package'
                            bat ''' mvn clean verify package -Dmaven.plugin.validation=brief sonar:sonar -Dsonar.projectKey=rnd-springboot-3.0 -Dsonar.projectName='rnd-springboot-3.0' -Dsonar.host.url=http://localhost:9000 '''
                            echo 'SonarQube Analysis Completed'
                        } catch (err) {
                            withCredentials([string(credentialsId: 'telegram-token', variable: 'TOKEN'), string(credentialsId: 'telegram-chat-id', variable: 'CHAT_ID')]) {
                                bat '''
                                    curl -s -X POST https://api.telegram.org/bot"%TOKEN%"/sendMessage -d chat_id="%CHAT_ID%" -d parse_mode="HTML" -d text="<b>Project</b> : rnd-springboot-3.0 \
                                    <b>Branch</b>: master \
                                    <b>Status</b> : Build Failed at SonarQube Analysis Stage"
                                '''
                                bat 'exit 1'
                            }
                        }
                    }
                }
            }
        }
        stage("Quality Gate") {
            steps {
                waitForQualityGate abortPipeline: true
                echo 'Quality Gate Completed'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        bat 'docker build -t septianreza/rnd-springboot-3.0 .'
                        echo 'Build Docker Image Completed'
                    } catch (err) {
                        withCredentials([string(credentialsId: 'telegram-token', variable: 'TOKEN'), string(credentialsId: 'telegram-chat-id', variable: 'CHAT_ID')]) {
                            bat '''
                                curl -s -X POST https://api.telegram.org/bot"%TOKEN%"/sendMessage -d chat_id="%CHAT_ID%" -d parse_mode="HTML" -d text="<b>Project</b> : rnd-springboot-3.0 \
                                <b>Branch</b>: master \
                                <b>Status</b> : Build Failed at Build Docker Image Stage"
                            '''
                            bat 'exit 1'
                        }
                    }
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    try {
                        withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhub-password')]) {
                            bat ''' docker login -u septianreza -p "%dockerhub-password%" '''
                        }
                        bat 'docker push septianreza/rnd-springboot-3.0'
                    } catch (err) {
                        withCredentials([string(credentialsId: 'telegram-token', variable: 'TOKEN'), string(credentialsId: 'telegram-chat-id', variable: 'CHAT_ID')]) {
                            bat '''
                                curl -s -X POST https://api.telegram.org/bot"%TOKEN%"/sendMessage -d chat_id="%CHAT_ID%" -d parse_mode="HTML" -d text="<b>Project</b> : rnd-springboot-3.0 \
                                <b>Branch</b>: master \
                                <b>Status</b> : Build Failed at Docker Push Stage"
                            '''
                            bat 'exit 1'
                        }
                    }
                }
            }
        }

        stage ('Docker Run') {
            steps {
                script {
                    try {
                        bat 'docker run -d --name rnd-springboot-3.0 -p 8099:8080 septianreza/rnd-springboot-3.0'
                        echo 'Docker Run Completed'
                    } catch (err) {
                        withCredentials([string(credentialsId: 'telegram-token', variable: 'TOKEN'), string(credentialsId: 'telegram-chat-id', variable: 'CHAT_ID')]) {
                            bat '''
                                curl -s -X POST https://api.telegram.org/bot"%TOKEN%"/sendMessage -d chat_id="%CHAT_ID%" -d parse_mode="HTML" -d text="<b>Project</b> : rnd-springboot-3.0 \
                                <b>Branch</b>: master \
                                <b>Status</b> : Build Failed at Docker Run Stage"
                            '''
                            bat 'exit 1'
                        }
                    }
                }
            }
        }

        stage('Build Successfully') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'telegram-token', variable: 'TOKEN'), string(credentialsId: 'telegram-chat-id', variable: 'CHAT_ID')]) {
                        bat '''
                            curl -s -X POST https://api.telegram.org/bot"%TOKEN%"/sendMessage -d chat_id="%CHAT_ID%" -d parse_mode="HTML" -d text="<b>Project</b> : rnd-springboot-3.0 \
                            <b>Branch</b>: master \
                            <b>Test suite</b> = Passed \
                            <b>Status</b> : Build Successfully"
                        '''
                    }
                }
            }
        }

    }
    post {
        always {
            bat 'docker logout'
        }
    }
}