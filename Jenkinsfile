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
                sh '''
                    docker build \
                        -t anweshanthati/movie_booking_backend:${GIT_COMMIT} .
                '''
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | \
                            docker login -u "$DOCKER_USERNAME" --password-stdin

                        docker push \
                            anweshanthati/movie_booking_backend:${GIT_COMMIT}

                        docker logout
                    '''
                }
            }
        }

        stage('Deploy') {
    steps {
        withCredentials([
            sshUserPrivateKey(
                credentialsId: 'movie-booking-vm-ssh',
                keyFileVariable: 'SSH_KEY',
                usernameVariable: 'SSH_USER'
            )
        ]) {
            sh '''
                OLD_TAG=$(ssh -o StrictHostKeyChecking=no \
                    -i "$SSH_KEY" \
                    "$SSH_USER@34.14.157.155" \
                    "grep '^BACKEND_IMAGE_TAG=' /home/anwesh_anthati/.env | cut -d= -f2")

                echo "Previous image tag: $OLD_TAG"
                echo "New image tag: $GIT_COMMIT"

                ssh -o StrictHostKeyChecking=no \
                    -i "$SSH_KEY" \
                    "$SSH_USER@34.14.157.155" \
                    "sed -i 's/^BACKEND_IMAGE_TAG=.*/BACKEND_IMAGE_TAG=${GIT_COMMIT}/' /home/anwesh_anthati/.env"

                ssh -o StrictHostKeyChecking=no \
                    -i "$SSH_KEY" \
                    "$SSH_USER@34.14.157.155" \
                    "cd /home/anwesh_anthati && docker compose pull springboot"

                ssh -o StrictHostKeyChecking=no \
                    -i "$SSH_KEY" \
                    "$SSH_USER@34.14.157.155" \
                    "cd /home/anwesh_anthati && docker compose up -d springboot"

                echo "Running health check..."

                if ssh -o StrictHostKeyChecking=no \
                    -i "$SSH_KEY" \
                    "$SSH_USER@34.14.157.155" \
                    "curl --fail http://localhost:8080/actuator/health"; then

                    echo "Deployment successful!"

                else

                    echo "Health check failed!"
                    echo "Rolling back to $OLD_TAG"

                    ssh -o StrictHostKeyChecking=no \
                        -i "$SSH_KEY" \
                        "$SSH_USER@34.14.157.155" \
                        "sed -i 's/^BACKEND_IMAGE_TAG=.*/BACKEND_IMAGE_TAG=$OLD_TAG/' /home/anwesh_anthati/.env"

                    ssh -o StrictHostKeyChecking=no \
                        -i "$SSH_KEY" \
                        "$SSH_USER@34.14.157.155" \
                        "cd /home/anwesh_anthati && docker compose pull springboot && docker compose up -d springboot"

                    exit 1
                fi
            '''
        }
    }
}
    }
}
