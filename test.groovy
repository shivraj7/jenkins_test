Boolean bool = false

pipeline {
  agent any
  stages {
    stage ('Stage 1') {
      steps {
        echo "hello1 "
      }
    }
    stage ('Stage 2') {
      when {
        expression { bool }
      }
      steps {
        echo "hello2"
      }
    }
    stage ('Stage 3') {
      steps {
        echo "hello3"
      }
    }
  }
}
