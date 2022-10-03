# Git Commands Summary

## Git Config
 - git config --global user.name "John Doe"
 - git config --global user.email

## Git Commands
- git clone ....
- git status
- git checkout -b branch-name  (for creating new branch)
- git add filename/folder (for adding filename to be ready to commit)
- git commit -m “message …..”
- git push origin feature-1464-real-time-notification(use - u the first time we push to remote)
- git push -f (after a rebase was done, be careful with this one)


## Git Branches
- git stash
- git stash apply
- git stash list
- git stash drop
- git stash pop
- git branch (list all branches)
- git branch -d branch-name
- git branch -D branch-name
- git checkout branch-name

## Git Merge
- git merge branch-name
- git merge --abort
- git merge --continue
- git merge --no-ff branch-name
- git merge --squash branch-name
- git merge --strategy=ours branch-name
- git merge --strategy=theirs branch-name
- git merge --strategy-option=theirs branch-name
- git merge --strategy-option=ours branch-name
- git merge --strategy-option=theirs branch-name
- git merge --strategy-option=ours branch-name


## Git Rebase
- git rebase branch-name
- git rebase --abort
- git rebase --continue
- git rebase --skip
- git rebase -i HEAD~3(3 is the number of commits, and it will open the editor to edit the commits)

## Git Reset/Restore
git reset HEAD path/to/unwanted_file​
git restore path/to/unwanted_file

- git reset --soft HEAD^(Remove the last commit from the current branch, but the file changes will stay in your working tree)
- git commit --amend (to change the last commit message)