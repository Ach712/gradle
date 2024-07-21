job('example') {
    // Configure log rotation to keep the last 10 builds
    logRotator(-1, 10)
    
    // Use Java 8 JDK
    jdk('Java 8')
    
    // Configure SCM to checkout the repository from GitHub
    scm {
        git("git@github.com:Ach712/gradle.git", 'main') // Change to your repository and branch
    }
    
    // Set up a trigger to build on every push to GitHub
    triggers {
        scm('* * * * * ')
    }
    
    // Define the steps to build and run the project
    steps {
        gradle {
            tasks('clean build') // Build the project using Gradle
            switches('--no-daemon') // Optional: Add any Gradle switches if needed
            useWrapper(true) // Use Gradle wrapper
        }
        
        // Add a shell step to run the project
        shell('chmod +x ./gradlew && ./gradlew run')
    }
    
    // Configure post-build actions
    publishers {
        // Archive the built JAR file(s)
        archiveArtifacts('build/libs/*.jar')
    }
}
