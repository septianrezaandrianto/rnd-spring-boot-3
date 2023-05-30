#!groovy
pipeline {
    agent none
    stages {
        stage('Maven Install') {
    	    agent {
                docker {
        	        image 'maven:3.9.1'
                }
            }
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Docker Build') {
    	agent {
    	    docker {
                steps {
                    bat 'echo "Docker Build ........."'
                    sh 'docker build -t rnd-springboot-3 .'
                }
            }
        }
    }
}