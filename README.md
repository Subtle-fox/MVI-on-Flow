## Implementation of MVI pattern based on Flow

The basic entity is called `Feature` which has an input `action` and output `state` and can emit `event`

<img width="250" alt="Снимок экрана 2022-09-19 в 19 54 29" src="https://user-images.githubusercontent.com/5879172/191072332-d9630928-4d30-4ce0-9895-27493ab5a650.png">

Feature extends Flow and implements FlowCollector. 

<img width="250" alt="Снимок экрана 2022-09-19 в 19 54 39" src="https://user-images.githubusercontent.com/5879172/191072428-dac22741-037d-48df-baba-3f8cfcc52b51.png">

Thus, it is possible to combine them as regular Kotlin's Flows:

<img width="555" alt="Снимок экрана 2022-09-19 в 19 56 25" src="https://user-images.githubusercontent.com/5879172/191072508-fd3e4fc7-4a02-453f-8c58-b35a1d28b24d.png">
<img width="600" alt="Снимок экрана 2022-09-19 в 19 54 57" src="https://user-images.githubusercontent.com/5879172/191072515-ea897080-177a-4315-ac7c-e700626bb311.png">

This picture shows how data flows inside of the Feature 

<img width="700" alt="Снимок экрана 2022-09-19 в 19 57 14" src="https://user-images.githubusercontent.com/5879172/191072590-91a4ee7d-c9c3-4cbe-96c7-58788ba3e369.png">

There is no obligation to use each component. We can emit either Actor, Bootstrap or EventProducer or all of them

<img width="700" alt="Снимок экрана 2022-09-19 в 19 57 23" src="https://user-images.githubusercontent.com/5879172/191072703-82a261ec-8c4d-40fc-badb-8aff73ad2f94.png">
<img width="700" alt="Снимок экрана 2022-09-19 в 19 57 44" src="https://user-images.githubusercontent.com/5879172/191072707-50bfc52d-c2d3-4e1d-a824-a6aa21496a39.png">


Samples located in app module

### Sample 1

Simple screen with no actions. Preloads data on start

### Sample 2

Screen with periodic updates based on selected currency. Simulated exception for Usd

https://user-images.githubusercontent.com/5879172/182160594-676b36fc-820a-4cfb-beac-807ce7d35d96.mov


### Sample 3

Screen with countries list, pagination and navigation

https://user-images.githubusercontent.com/5879172/182159683-c0233c1e-db4c-4c96-b524-5b7b5fccd92d.mov


### Sample 4

Sample of how sequence of dependant actions can be implemented: 

https://user-images.githubusercontent.com/5879172/182425025-6c8f1994-b626-4d71-81fc-6d89f44416b2.mov


### Sample 5

Demonstrate how to you SavedStateHandle to restore last filter after Activity being killed (in demo flag "don't keep activities" is enbled)

### Sample 6

Demonstrate how to you SavedStateHandle to restore whole screen state after Activity being killed (in demo flag "don't keep activities" is enbled)

https://user-images.githubusercontent.com/5879172/182621141-7318ef64-ebe0-4df5-a7e2-7d5fa382c0d6.mov

### Sample 7

Demonstrates how 2 independent MviFeatures can be combined inside of single ViewModel (sample 2 and sample 5)
Note, that in real world this example is much easier to implement using simple fragment stacking

https://user-images.githubusercontent.com/5879172/182635509-513d7d67-f6ff-4bd2-8f79-3ce31d578b8d.mov



### Template 

Creates feature' components for you

https://user-images.githubusercontent.com/5879172/182161471-5f8be252-5219-4a09-91bb-31afce7c9a1a.mov




