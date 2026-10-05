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
        stage('Deploy') {
    steps {
        sshagent(['movie-booking-vm-ssh']) {
            sh '''
                ssh -o StrictHostKeyChecking=no anwesh_anthati@34.14.157.155 \
                "sed -i 's/^BACKEND_IMAGE_TAG=.*/BACKEND_IMAGE_TAG=${GIT_COMMIT}/' /home/anwesh_anthati/.env"

                ssh -o StrictHostKeyChecking=no anwesh_anthati@34.14.157.155 \
                "cd /home/anwesh_anthati && docker compose pull springboot"

                ssh -o StrictHostKeyChecking=no anwesh_anthati@34.14.157.155 \
                "cd /home/anwesh_anthati && docker compose up -d springboot"
            '''
        }
    }
}
    }
}
