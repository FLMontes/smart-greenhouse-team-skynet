#!/bin/sh
set -e

ROOT_DIR="$(git rev-parse --show-toplevel)"

echo "Installing Git hooks..."
cd "$ROOT_DIR"

if [ ! -d ".githooks" ]; then
    echo "Error: .githooks directory not found."
    exit 1
fi

if [ ! -f ".githooks/pre-commit" ]; then
    echo "Error: .githooks/pre-commit not found."
    exit 1
fi

if [ ! -f ".githooks/pre-push" ]; then
    echo "Error: .githooks/pre-push not found."
    exit 1
fi

git config core.hooksPath .githooks

echo "Git hooks installed successfully."
echo "Current hooks path:"
git config core.hooksPath