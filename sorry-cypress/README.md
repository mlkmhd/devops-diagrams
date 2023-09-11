# sorry cypress architecture 
in the following diagram, you can see the architecture of sorry cypress:
- Dashboard: the dashboard is for showing the test results to the tester. it collects & shows projects. inside each project there's some test cases and for each test case there's some videos or screenshots.
- API: the API is for fetching the dashboard contents like project list details.
- Director: The director is responsible to manage Test engines and distribute tasks between them. the test suit is also sends its test results to the director to be saved.
- MongoDB: the sorry-cypress uses mongodb to store its data. it is not used for videos & screenshots, it only used for store non-relational data like project list & test results
- Minio: the sorry-cypress uses minio to store videos & screenshots of each test case.

<p align="center">
  <img src="pictures/architecture.png?raw=true" />
</p>

you can refer to the following article on medium to learn more:
https://medium.com/itnext/sorry-cypress-quick-start-349f283ca258
