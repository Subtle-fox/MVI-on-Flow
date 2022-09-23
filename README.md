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
https://user-images.githubusercontent.com/5879172/191924824-3904bd97-984f-4569-a17f-ac1ece4dba76.gif


Samples located in app module

### Sample 1

Simple screen with no actions. Preloads data on start

![mvi-sample-1](https://user-images.githubusercontent.com/5879172/191935019-77fd5fbb-9037-458a-a885-555550280933.gif)


### Sample 2

Screen with periodic updates based on selected currency. Simulated exception for Usd

![mvi-sample-2](https://user-images.githubusercontent.com/5879172/191926104-27a0b4f7-da9a-4e14-87e7-00b792160f16.gif)

### Sample 3

Screen with countries list, pagination and navigation

![mvi-sample-3](https://user-images.githubusercontent.com/5879172/191931212-6822099d-63e0-4359-b47d-c6ef652cb0dd.gif)


### Sample 4

Sample of how sequence of dependant actions can be implemented: 

![mvi-sample-4](https://user-images.githubusercontent.com/5879172/191938812-f88ccba9-c50f-4940-b554-48821901768b.gif)


### Sample 5

Demonstrate how to you SavedStateHandle to restore last filter after Activity being killed (in demo flag "don't keep activities" is enbled)

![mvi-sample-6](https://user-images.githubusercontent.com/5879172/191946969-61ad4f09-55d1-4cdf-ae26-d572b2c149f1.gif)


### Sample 6

Demonstrate how to you SavedStateHandle to restore whole screen state after Activity being killed (in demo flag "don't keep activities" is enbled)

![mvi-sample-6](https://user-images.githubusercontent.com/5879172/191946969-61ad4f09-55d1-4cdf-ae26-d572b2c149f1.gif)


### Sample 7

Demonstrates how 2 independent MviFeatures can be combined inside of single ViewModel (sample 2 and sample 5)
Note, that in real world this example is much easier to implement using simple fragment stacking

![mvi-sample-7](https://user-images.githubusercontent.com/5879172/191947671-09961be9-3a9c-4ef3-96af-2e7a72a4ca64.gif)


### Template 

Creates feature' components for you

https://user-images.githubusercontent.com/5879172/182161471-5f8be252-5219-4a09-91bb-31afce7c9a1a.mov




