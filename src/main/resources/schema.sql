CREATE TABLE IF NOT EXISTS accounts (
                                        account_number INTEGER PRIMARY KEY,
                                        name TEXT NOT NULL,
                                        age INTEGER NOT NULL,
                                        balance REAL NOT NULL,
                                        account_type TEXT NOT NULL,
                                        status TEXT NOT NULL,
                                        pin INTEGER,
                                        tenure_years INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS transactions (
                                            transaction_id TEXT PRIMARY KEY,
                                            timestamp TEXT NOT NULL,
                                            account_number INTEGER NOT NULL,
                                            transaction_type TEXT NOT NULL,
                                            amount REAL NOT NULL,
                                            balance_after REAL NOT NULL,
                                            status TEXT NOT NULL,
                                            description TEXT,
                                            source_account INTEGER,
                                            destination_account INTEGER
);