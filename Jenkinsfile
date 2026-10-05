pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                sh '''
                    java -version
                    mvn -version
                    mvn test
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t anweshanthati/movie_booking_backend:${GIT_COMMIT} .'
            }
        }
        stage('Docker Push') {
    steps {
        withCredentials([usernamePassword(
            credentialsId: 'dockerhub-credentials',
            usernameVariable: 'DOCKER_USERNAME',
            passwordVariable: 'DOCKER_PASSWORD'
        )]) {
            sh '''
                echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                docker push anweshanthati/movie_booking_backend:${GIT_COMMIT}
                docker logout
            '''
        }
    }
}
    }
}
