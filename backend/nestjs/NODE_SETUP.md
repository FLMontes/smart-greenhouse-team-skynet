# Node.js Version Setup

This project uses **Node.js 22 LTS**. It is recommended to use [nvm](https://github.com/nvm-sh/nvm) to manage your Node.js version.

## Prerequisites

- [nvm](https://github.com/nvm-sh/nvm#installing-and-updating) installed on your machine

## Setup

1. Install the required Node version:
   ```bash
   nvm install
   ```

2. Switch to the correct version:
   ```bash
   nvm use
   ```

3. Verify:
   ```bash
   node --version  # should print v22.x.x
   ```

> The `.nvmrc` file at the root of the project pins the Node version automatically when you run `nvm use`.

## Why Node 22?

Node 22 is the current **LTS (Long Term Support)** release, which means it is stable, well documented, and supported until April 2027. Avoid using Node 23+ or Node 25 as they are not LTS and may cause compatibility issues with some dependencies.
