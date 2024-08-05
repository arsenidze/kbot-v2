pipeline {
    agent any
    parameters {
        choice(name: 'OS', choices: ['linux', 'darwin', 'windows', 'all'], description: 'Pick OS')
        choice(name: 'ARCH', choices: ['amd64', 'arm64'], description: 'Pick ARCH')
    }
    stages {
        stage('Test') {
            steps {
                sh 'make test'
            }
        }
        stage('Build&Push') {
            steps {
                sh "make image push TARGETARCH=${params.ARCH}"
            }
        }
        stage('Clean') {
            steps {
                sh "make clean TARGETARCH=${params.ARCH}"
            }
        }
    }
}
