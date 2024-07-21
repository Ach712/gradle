job('example') {
    // General configuration
    properties {
        githubProjectUrl('https://github.com/Ach712/gradle') // GitHub project URL without .git
    }
    
    // Configure log rotation to keep the last 10 builds
    logRotator {
        numToKeep(10)
    }
    
    // Use Java 17 JDK
    jdk('Java 17')
    
    // Source Code Management
    scm {
        git {
            remote {
                url('git@github.com:Ach712/gradle.git')
            }
            branches('main')
        }
    }
    
    // Build Triggers
    triggers {
        scm('* * * * *')
    }
    
    // Build Steps
    steps {
        gradle {
            tasks('clean build') // Build the project using Gradle
            useWrapper(true) // Use Gradle wrapper
        }
        
        // Add a shell step to run the project
        if (isUnix()) {
            shell('chmod +x ./gradlew && ./gradlew run')
        } else {
            bat('gradlew.bat run')
        }
    }
    
    // Post-build Actions
    publishers {
        // Archive the built JAR file(s)
        archiveArtifacts('build/libs/*.jar')
    }
}
