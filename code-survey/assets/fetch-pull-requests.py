import os
import requests
import json

# Replace with your GitHub username, repository, and personal access token (PAT)
USERNAME = "kubernetes"
REPO = "kubernetes"
TOKEN = os.environ['GITHUB_TOKEN']

# GitHub API endpoint for pull requests
url = f"https://api.github.com/repos/{USERNAME}/{REPO}/pulls?state=all&per_page=100"

# Headers for authorization
headers = {
    'Authorization': f'token {TOKEN}',
    'Accept': 'application/vnd.github.v3+json'
}


def fetch_pull_requests(page=1):
    """
    Fetch pull requests from the GitHub API.
    """
    response = requests.get(url + f"&page={page}", headers=headers)

    if response.status_code == 200:
        return response.json()
    else:
        print(f"Failed to fetch pull requests: {response.status_code}")
        return []


def save_to_json(data, filename):
    """
    Save pull requests to a JSON file.
    """
    with open(filename, 'w') as f:
        json.dump(data, f, indent=4)
        print(f"Saved to {filename}")


def fetch_and_save_all_prs():
    """
    Fetch all pull requests from the repository and save them as JSON files.
    """
    page = 1

    while True:
        print(f"Fetching page {page}...")
        prs = fetch_pull_requests(page)
        if not prs:
            break
        for pr in prs:
            save_to_json(pr, f"pull_requests/pull_requests_page_{pr["number"]}.json")
        page += 1


# Execute the function to fetch and save pull requests
fetch_and_save_all_prs()
