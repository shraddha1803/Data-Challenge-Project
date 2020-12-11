# Data-Challenge-Project

# README
### path(Rent and Vacancy Control Agent-Based Model)

*This is the main directory for data and support files related to the Data Bytes | Data Challenge Submission.*

### Team name: Data Bytes
### Proposition 21
### last updated: 4/10/20
### last update by: Colton Connor


### Purpose/motivation
Research question: Will rent and vacancy control encourage or discourage stability in renters? 

In recent years, California has been undergoing a housing crisis, fueled by a high demand for housing compared to a low supply. With high rents in many parts of California, such as the Bay Area, affordability of housing has remained an issue that has been discussed for quite a while. Proposition 21 essentially removes some of the disadvantages caused by the Costa-Hawkins Rental Housing Act, thereby allowing rent control to be applied to more properties than the current level.

Our project aims to investigate how rent and vacancy control will affect the movement of renters' behaviour over time by creating an agent-based model that uses common data about income and rent levels in California to predict and track what effect rent and vacancy control would have on people's incomes, such as moving out of their homes. 



### Directory Manifest

*  Folders:
	* DataChallenge2020 - This contains all code for the agent-based model. The model is dependent on MASON, MASONplus4, and Apache POI.
	Apache POI was too big to download into repository but can be found here: https://poi.apache.org/download.html#POI-4.1.2
	MASON and MASONplus4 can be unzipped from DataChallenge2020/MASON.zip and DataChallenge2020/MASONplus4.zip or downloaded from: https://cs.gmu.edu/~eclab/projects/mason/
	* Conclusions and Visualizations - Three Excel files that recorded output from our model. Three graphs made in Excel plotting the recorded output.

* Files:
	* DataChallenge2020/src/RentControlDataModel/AfterStep.java - Class used to write to Excel sheets and update output after each step. Must manually change file name in 		code to the Excel worksheet you want to write too.
	* DataChallenge2020/src/RentControlDataModel/Agents.java - Class that creates a tenant with income that is updated. Also has rules for when to move.
	* DataChallenge2020/src/RentControlDataModel/Evironment.java - Class that controls overall model and creates Homes and Agents.
	* DataChallenge2020/src/RentControlDataModel/GUIsim.java - Class that creates the GUI output screen of the simulation.
	* DataChallenge2020/src/RentControlDataModel/Home.java - Class that creates a home with a rent price that is updated. Also has rules for rent and vacancy control.
	All code files have comments explaining what code does in further detail
	
	* Conclusions and Visualizations/Average Percent Change in Income from Year to Year In California.xlsx - Calculated using Census data, used to update income every step.
	* Conclusion and Visualization/Change in Number of Tenants Able to Afford Homes per Income Level with No Rent Control or Vacancy Control.png - Visualization #1
	* Conclusion and Visualization/Change in Number of Tenants Able to Afford Homes per Income Level with Rent Control and No Vacancy Control.png - Visualization #2
	* Conclusion and Visualization/Change in Number of Tenants Able to Afford Homes per Income Level with Rent Control and Vacancy Control.png - Visualization #3
	* Conclusions and Visualizations/WithNoRentControl.xlsx - Excel sheet produced by model when rent and vacancy control false.
	* Conclusions and Visualizations/WithRentControlNoVacancy.xlsx - Excel sheet produced by model when rent control was true and vacancy control false.
	* Conclusions and Visualizations/WithRentControlAndVacancy.xlsx - Excel sheet produced by model when rent and vacancy control true.

### Personnel/Contributors

* Colton Connor - Team Lead - cmconnor@ucdavis.edu
* Shraddha Jhingan - Team Member - sjhingan@ucdavis.edu


### Project URLs - (for example: to data sources you used for the project)

* https://data.census.gov/cedsci/table?g=0400000US06_1600000US0667000&d=ACS%205-Year%20Estimates%20Data%20Profiles&tid=ACSDP5Y2018.DP03&hidePreview=false - Census data used for mean household income numbers from 2010 to 2018 that were used in Conclusions and Visualizations/Average Percent Change in Income from Year to Year In California.xlsx in order to calculate average percent change in income from year to year, this was used to update income every step in our model.
* https://data.census.gov/cedsci/table?g=0400000US06_1600000US0667000&d=ACS%205-Year%20Estimates%20Data%20Profiles&tid=ACSDP5Y2018.DP04 - Census data used for estimated number of rental units per GROSS RENT range in California, this was used to initialize the homes in our model.

### Project Repositories
* Main Repository: https://github.com/shraddha1803/Data-Challenge-Project.git

### Project Overview Video
https://www.youtube.com/watch?v=vJr4DbiJiuY
