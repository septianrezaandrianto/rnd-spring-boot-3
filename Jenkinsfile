node {
	    // reference to maven
	    // ** NOTE: This 'maven-3.5.2' Maven tool must be configured in the Jenkins Global Configuration.
	    def mvnHome = tool 'maven-3.9.1'


	    // holds reference to docker image
	    def dockerImage
	    // ip address of the docker private repository(nexus)

	    def dockerImageTag = "rnd-springboot-3${env.BUILD_NUMBER}"

	    stage('Clone Repo') { // for display purposes
	      // Get some code from a GitHub repository
	      git 'https://github.com/septianrezaandrianto/rnd-spring-boot-3.git'
	      // Get the Maven tool.
	      // ** NOTE: This 'maven-3.5.2' Maven tool must be configured
	      // **       in the global configuration.
	      mvnHome = tool 'maven-3.9.1'
	    }

	    stage('Build Project') {
	      // build project via maven
	      sh "'${mvnHome}/bin/mvn' clean install"
	    }

	    stage('Build Docker Image') {
	      // build docker image
	      dockerImage = docker.build("rnd-springboot-3:${env.BUILD_NUMBER}")
	    }

	    stage('Deploy Docker Image'){

	      // deploy docker image to nexus

	      echo "Docker Image Tag Name: ${dockerImageTag}"

		  sh "docker stop rnd-springboot-3"

		  sh "docker rm rnd-springboot-3"

		  sh "docker run --name rnd-springboot-3 -d -p 8090:8090 rnd-springboot-3:${env.BUILD_NUMBER}"

		  // docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
	      //    dockerImage.push("${env.BUILD_NUMBER}")
	      //      dockerImage.push("latest")
	      //  }

	    }