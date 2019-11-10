# -*- coding: utf-8 -*-
"""
Created on Sat Nov  9 16:59:50 2019

@author: Joe
"""

from google.cloud import bigquery

# Create a "Client" object
client = bigquery.Client()


# Construct a reference to the "hacker_news" dataset
dataset_ref = client.dataset("hacker_news", project="bigquery-public-data")

# API request - fetch the dataset
dataset = client.get_dataset(dataset_ref)


# List all the tables in the "hacker_news" dataset
tables = list(client.list_tables(dataset))

# Print names of all tables in the dataset (there are four!)
for table in tables:  
    print(table.table_id)