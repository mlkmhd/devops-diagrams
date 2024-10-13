from openai import OpenAI
import os
import json
import requests
import csv

OPENAI_API_KEY = os.environ['OPENAI_API_KEY']


# Function to load the survey from survey.yaml
def load_survey(survey_file):
    with open(survey_file, 'r') as file:
        survey = file.read()
    return survey


# Function to load pull request data from a JSON file
def load_pull_request(pr_file):
    with open(pr_file, 'r') as file:
        pull_request = json.load(file)
    return pull_request


# Function to send pull request and survey data to OpenAI API
def analyze_pull_request(pull_request, survey):
    client = OpenAI(
        api_key=OPENAI_API_KEY
    )
    response = client.chat.completions.with_raw_response.create(
        messages=[{
            "role": "user",
            "content": f"please analyze the pull request and response to the survey. here is the pull request: \n{pull_request} \n\n\nand here is the survey: \n{survey}",
        }],
        model="gpt-4o-mini",
    )
    completion = response.parse()
    return response


# Function to write results to a CSV file
def write_to_csv(filename, analysis_results, pr_filename):
    with open(filename, mode='a', newline='') as file:
        writer = csv.writer(file)
        # Write header only once if the file is new
        if os.stat(filename).st_size == 0:
            writer.writerow(["PR File", "Question", "Answer"])

        # Write the results for this pull request
        for question, answer in analysis_results.items():
            writer.writerow([pr_filename, question, answer])


# Load survey from survey.yaml
survey_file = 'survey.yaml'
survey = load_survey(survey_file)

# Directory containing pull request JSON files
pull_requests_dir = 'pull_requests'
csv_filename = 'analysis_results.csv'

# Loop through all JSON files in the directory
for pr_file in os.listdir(pull_requests_dir):
    pr_filepath = os.path.join(pull_requests_dir, pr_file)
    print(f"Processing {pr_filepath}...")

    # Load pull request data
    pull_request = load_pull_request(pr_filepath)

    # Send to OpenAI API and get response
    response = analyze_pull_request(pull_request, survey)

    if response.status_code == 200:
        analysis_results = response.json()
        # Write results to CSV
        write_to_csv(csv_filename, analysis_results, pr_file)
    else:
        print(f"Failed to analyze {pr_file}. Status Code: {response.status_code}")
        print(response.text)

print(f"Analysis completed and saved to {csv_filename}")
