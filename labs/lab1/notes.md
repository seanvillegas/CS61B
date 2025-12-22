#### Prof Hug
## Lab 1 
#### Sean Villegas


_focusing on efficiency of writing and running programs_

## Intro to Java

```python
print('HW')
```

V.S.

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
```

- Everything in java is a `class`
- name of the class `HelloWorld`
- The method name is `main`, where code to print `'Hello World'` is executed
- Notice the `;` to separate statements 


## Github
- Git is a distributed version control system.
    - Version Control System: Tracks all changes to files over time.
    - Distributed: Every developer’s computer stores the entire history of the project.
    - Entire history of a project is called a repository 

### Steps you should know as a programmer
[Visualize](https://git-school.github.io/visualizing-git/#free-remote)
 - `git init`
 - `git add <file_name or .>`
 - `git commit -m "Message"`
    - Saves all staged files into a commit (like a snapshot of your current repository) 
 - `git status` 
    - Displays state of the repository and staging area (tracked, untracked files and changes). 
 - `git log` 
    -  Displays the history of our committed history.
 - `git restore` 
    - restores files to their versions in most recent commit 
- `git restore --source=[commitID]`
    - restores files ot their versions in given commit ID
- `git push <repo> <branch>`
    - Takes commits on your local computer and pushes it into the remote repository (git push origin main)
- `git pull <repo> <branch>`
    - pulls changes from remote repository onto your computer (`git pull skeleton main`)
- If we ever want to get something from our remote to local repository, we would use `git pull [repo] [branch]`


## Lab
- To test if everything is working correctly, run the Arithmetic class by opening the file, clicking on the green triangle next to public class Arithmetic, then clicking “`Run ‘Arithmetic.main()’`”.

