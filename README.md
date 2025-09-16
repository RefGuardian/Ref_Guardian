# Ref_Guardian

  - [General Introduction](##General-Introduction)
  - [Contents of the Replication Package](##Contents-of-the-Replication-Package)
  - [Requirements](##Requirements)
  - [Replicate the Evaluation](##How-to-Replicate-the-Evaluation)

## General Introduction

This is the replication package for FSE submission, containing both tool and data that are requested by the replication. It also provides detailed instructions to replicate the evaluation.

## Contents of the Replication Package

- /data: Benchmark Datasets(HisRef & LLMRef)
- /RefGuardian: The implementation of RefGuardian

## Requirements
- Java >= 17

## How to Replicate the Evaluation

   1. **Import project**

      `Go to *File* -> *open*`

      Browse to the `RefGuardian` directory

      `Click *OK* -> *Trust Project*`
  
   2. **Run the experiment**
       
       You need to modify several variables in the code:
       
       - `apiKey` to your ChatGPT API Key.
       - `pathToTheDataset` to the path where the dataset files are located.

       If you want to start the replication for RefGuardian, run `test/java/test.java` 