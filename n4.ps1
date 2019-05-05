Start-Process console.exe -Verb runAs -WorkingDirectory "C:\Niagara\Niagara-4.6.96.28\bin" #runAs verb grants eleveted access, but it disables the -RedirectStandardInput switch!
Start-Process console.exe -WorkingDirectory "C:\Niagara\Niagara-4.6.96.28\bin" -RedirectStandardInput "C:\Users\David\Niagara4.6\vykon\Smolinski_Victor_review\wb.txt" #requires elevated access!
$n4Console = Get-Process -Name console #get a reference to the n4 console process
get-member -InputObject $n4Console #dump all the members in the referenced object passed
