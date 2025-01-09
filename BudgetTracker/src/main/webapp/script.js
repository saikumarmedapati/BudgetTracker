const transactions = [];
    let accountHolder = {};

    // Handle account holder form submission
    document.getElementById('accountHolderForm').addEventListener('submit', function (e) {
        e.preventDefault();
        accountHolder = {
            accountNumber: document.getElementById('accountNumber').value,
            fullName: document.getElementById('fullName').value,
            mobile: document.getElementById('mobile').value
        };
        updateAccountStatement();
    });

    // Add transaction function
    function addTransaction() {
        // Get form values
        const txnNumber = document.getElementById('txnNumber').value;
        const txnDate = document.getElementById('txnDate').value;
        const header = document.getElementById('header').value;
        const credit = parseFloat(document.getElementById('credit').value) || 0;
        const debit = parseFloat(document.getElementById('debit').value) || 0;

        // Create transaction object
        const txn = { txnNumber, txnDate, header, credit, debit };
        transactions.push(txn);

        // Update transaction list and account statement
        updateTransactionList();
        updateAccountStatement();
    }

    // Toggle transaction form visibility
    document.getElementById('showTransactionFormBtn').addEventListener('click', function () {
        // Show the transaction form and hide the profile form
        document.getElementById('transactionForm').style.display = 'block';
        document.getElementById('profileForm').style.display = 'none';
    });

    // Toggle profile form visibility
    document.getElementById('showUserFormBtn').addEventListener('click', function () {
        // Show the profile form and hide the transaction form
        document.getElementById('profileForm').style.display = 'block';
        document.getElementById('transactionForm').style.display = 'none';
    });

    // Initialize by showing the profile form and hiding the transaction form
    document.getElementById('transactionForm').style.display = 'none';  // Hide transaction form by default
    document.getElementById('profileForm').style.display = 'block';  // Show profile form by default