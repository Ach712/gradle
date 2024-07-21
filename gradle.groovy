job('example') {
    // General configuration
    description('A Jenkins job to build and run a Gradle project from GitHub.')
    keepDependencies(false)
    
    // Configure log rotation to keep the last 10 builds
    logRotator {
        numToKeep(10)
    }
    
    // Use Java 17 JDK
    jdk('Java 17')
    
    // Configure SCM to checkout the repository from GitHub
    scm {
        git {
            remote {
                url('https://github.com/Ach712/gradle.git')
            }
            branch('main')
        }
    }
    
    // Set up a trigger to build on every push to GitHub
    triggers {
        scm('* * * * *')
    }
    
    // Define the steps to build and run the project
    steps {
        gradle {
            useWrapper(true)
            tasks('clean build run')
            switches('--no-daemon')
        }
    }
    
    // Configure post-build actions
    publishers {
        archiveArtifacts {
            pattern('build/libs/*.jar')
            onlyIfSuccessful()
        }
    }
}
