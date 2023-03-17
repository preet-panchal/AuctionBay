## How to Run:
**Application Build:** Java version 17.0.2

1) Clone repo or download ZIP folder
2) Open project via IntelliJ IDEA (https://www.jetbrains.com/idea/)
3) Within project structure (on the left hand side) and expand `AuctionBay/PHASE-4/Backend/src/main`:
4) Right-click on Main.java and select `Run 'Main.main()'`

# Note:
* If you are having issues reading the input files (ie. daily_transaction.txt, current_users_accounts.txt, available_items.txt) then please do the following steps:
  1) Expand the file structure on IntelliJ (left-hand side)
  2) Right click on `"daily_transaction.txt"` 
     1) Click on `Copy Path/Reference`
     2) Copy Absolute Path and replace it with the existing value for `mergedTransFile`.
  3) Perform Task (ii) for:
     1) Copy Absolute Path for `"current_users_accounts.txt"` for `oldUserAccFile`. 
     2) Copy Absolute Path for `"available_items.txt"` for `oldAvailItemsFile`.
