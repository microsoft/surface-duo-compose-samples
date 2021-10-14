/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.models

import com.microsoft.device.display.samples.navigationrail.R

object DataProvider {
    private val images: List<Image> = listOf(
        Image(
            id = 1,
            image = R.drawable.plant_1,
            name = "Asystasia gangetica",
            location = "North America, Europe",
            condition1 = "direct sunlight",
            condition2 = "2 meters",
            details = "Asystasia gangetica is usually seen in parts of North America and Europe. It enjoys cold climate, receiving direct light and is usually with smaller roots. In their best conditions they reach about a height around 1.5 meter and sometimes around 2 meters.",
        ),
        Image(
            id = 2,
            image = R.drawable.plant_2,
            name = "Ipomoea cairica",
            location = "Asia, Europe, Americas, Africa",
            condition1 = "direct sunlight",
            condition2 = "2 meters",
            details = "Ipomoea cairica is usually seen in parts of Asia, Africa, Europe, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and 2 meters."
        ),
        Image(
            id = 3,
            image = R.drawable.plant_3,
            name = "Dioscorea bulbifera",
            location = "India, North America, South America, Africa",
            condition1 = "direct sunlight",
            condition2 = "1.6 meters",
            details = "Dioscorea bulbifera is usually seen in parts of Asia, Africa, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and sometimes around 1.6 meters.",
        ),
        Image(
            id = 4,
            image = R.drawable.plant_4,
            name = "Afrocarpus falcatus",
            location = "Europe",
            condition1 = "direct sunlight",
            condition2 = "10 meters",
            details = "Afrocarpus falcatus is usually seen in parts of Europe. It enjoys damp climate in the rainforest, receiving direct light and is usually seen closer to the water streams with deeper roots extending for water. In their best conditions they reach about a height around 8 meter and sometimes around 10-12 meters.",
        ),
        Image(
            id = 5,
            image = R.drawable.plant_5,
            name = "Pilea cadierei",
            location = "India, North America, South America, Africa",
            condition1 = "direct sunlight",
            condition2 = "1.6 meters",
            details = "Pilea cadierei is usually seen in parts of Asia, Africa, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and sometimes around 1.6 meters.",
        ),
        Image(
            id = 6,
            image = R.drawable.plant_6,
            name = "Matteuccia struthiopteris",
            location = "Asia, Europe, Americas, Africa",
            condition1 = "direct sunlight",
            condition2 = "2 meters",
            details = "Matteuccia struthiopteris is usually seen in parts of Asia, Africa, Europe, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and 2 meters.",
        ),
        Image(
            id = 7,
            image = R.drawable.plant_7,
            name = "Curcuma longa",
            location = "India, North America, South America, Africa",
            condition1 = "direct sunlight",
            condition2 = "1.6 meters",
            details = "Curcuma longa is usually seen in parts of Asia, Africa, and Americas. It enjoys cool to moderate climate in the rainforest, receiving direct light through the canopy. In their best conditions they reach about a height around 1 meter and sometimes around 1.6 meters.",
        ),
        Image(
            id = 8,
            image = R.drawable.plant_8,
            name = "Aegopodium podagraria",
            location = "North America, Europe",
            condition1 = "direct sunlight",
            condition2 = "2 meters",
            details = "Aegopodium podagraria is usually seen in parts of North America and Europe. It enjoys cold climate, receiving direct light and is usually with smaller roots. In their best conditions they reach about a height around 1.5 meter and sometimes around 2 meters.",
        ),
        Image(
            id = 9,
            image = R.drawable.plant_9,
            name = "Homalomena rubescens",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 meters high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 10,
            image = R.drawable.plant_10,
            name = "Adiantum jordanii",
            location = "Southeast Asia",
            condition1 = "indirect filtered light",
            condition2 = "0.6 meters",
            details = "Adiantum jordanii is usually seen in southeast parts of Asia. It enjoys moist, humid environment of the rainforest floor, receiving indirect filtered light through the canopy. In their best conditions they reach about a height around 0.4 meters to 0.6 meters.",
        ),
        Image(
            id = 11,
            image = R.drawable.plant_11,
            name = "Agave sebastiana",
            location = "Africa",
            condition1 = "direct sunlight",
            condition2 = "0.3 meters",
            details = "Agave sebastiana is usually seen in parts of Africa. It enjoys dry climate, receiving direct light and is usually with deeper roots extending for water. In their best conditions they reach about a height around 0.2 meter and sometimes around 0.3 - 0.4 meters.",
        ),
        Image(
            id = 12,
            image = R.drawable.plant_12,
            name = "Chamaedorea elegans",
            location = "Europe",
            condition1 = "direct sunlight",
            condition2 = "10 meters",
            details = "Chamaedorea elegans is usually seen in parts of Europe. It enjoys damp climate in the rainforest, receiving direct light and is usually seen closer to the water streams with deeper roots extending for water. In their best conditions they reach about a height around 8 meter and sometimes around 10-12 meters.",
        ),
        Image(
            id = 13,
            image = R.drawable.bird_1,
            name = "Male common Nightingale",
            location = "Europe, Africa, Asia",
            condition1 = "wingspan 8-10 inches",
            condition2 = "",
            details = "The common nightingale, rufous nightingale or simply nightingale, is a small passerine bird best known for its powerful and beautiful song. It was formerly classed as a member of the thrush family Turdidae but is now more generally considered to be an Old World flycatcher, Muscicapidae.",
        ),
        Image(
            id = 14,
            image = R.drawable.bird_2,
            name = "Kingfisher",
            location = "Africa, Asia",
            condition1 = "",
            condition2 = "the largest kingfisher weighs around 500 grams",
            details = "Kingfishers or Alcedinidae are a family of small to medium-sized, brightly colored birds in the order Coraciiformes. They have a cosmopolitan distribution, with most species found in the tropical regions of Africa, Asia, and Oceania. The family contains 114 species and is divided into three subfamilies and 19 genera.",
        ),
        Image(
            id = 15,
            image = R.drawable.bird_3,
            name = "Resplendent Quetzal",
            location = "South America",
            condition1 = "",
            condition2 = "33-40 centimeters long",
            details = "The resplendent quetzal is a bird in the trogon family. It is found from Chiapas, Mexico to western Panama. It is well known for its colorful plumage. There are two subspecies, P. m. mocinno and P. m. costaricensis. The resplendent quetzal plays an important role in various types of Mesoamerican mythology.",
        ),
        Image(
            id = 16,
            image = R.drawable.bird_4,
            name = "Single Grey-headed Woodpecker",
            location = "Europe",
            condition1 = "wingspan 38-40 centimeters",
            condition2 = "",
            details = "The grey-headed woodpecker, also known as the grey-faced woodpecker, is a Eurasian member of the woodpecker family, Picidae. Along with the more commonly found European green woodpecker and the Iberian green woodpecker, it is one of three closely related sister species found in Europe.",
        ),
        Image(
            id = 17,
            image = R.drawable.bird_5,
            name = "Tawny Owl",
            location = "Europe, Asia",
            condition1 = "wingspan 100 centimeters",
            condition2 = "",
            details = "The tawny owl or brown owl is a stocky, medium-sized owl commonly found in woodlands across much of Eurasia and North Africa. Its underparts are pale with dark streaks, and the upperparts are either brown or grey. Several of the eleven recognised subspecies have both variants.",
        ),
        Image(
            id = 18,
            image = R.drawable.bird_6,
            name = "Wild Willow",
            location = "Europe, Africa",
            condition1 = "wingspan about 7 inches",
            condition2 = "",
            details = "The willow is a very common and widespread leaf warbler which breeds throughout northern and temperate Europe and the Palearctic, from Ireland east to the Anadyr River basin in eastern Siberia. It is strongly migratory, with almost all the population wintering in Sub-Saharan Africa.",
        ),
        Image(
            id = 19,
            image = R.drawable.bird_7,
            name = "Toucan",
            location = "South America",
            condition1 = "",
            condition2 = "grows up to 2 feet long",
            details = "Toucans are members of the Neotropical near passerine bird family Ramphastidae. The Ramphastidae are most closely related to the American barbets. They are brightly marked and have large, often-colorful bills. The family includes five genera and over forty different species.",
        ),
        Image(
            id = 20,
            image = R.drawable.bird_8,
            name = "Hummingbird",
            location = "North America",
            condition1 = "wingspan 4-4.75 inches",
            condition2 = "",
            details = "Hummingbirds are birds native to the Americas and comprise the biological family Trochilidae. With about 360 species, they occur from Alaska to Tierra del Fuego, but the vast majority of the species are found in the tropics. They are small birds, with most species measuring 7.5–13 cm in length.",
        ),
        Image(
            id = 21,
            image = R.drawable.bird_9,
            name = "Pileated Woodpecker",
            location = "North America",
            condition1 = "wingspan 30 inches",
            condition2 = "",
            details = "The pileated woodpecker is a large, mostly black woodpecker native to North America. An insectivore, it inhabits deciduous forests in eastern North America, the Great Lakes, the boreal forests of Canada, and parts of the Pacific Coast. It is the largest extant woodpecker species in the United States.",
        ),
        Image(
            id = 22,
            image = R.drawable.bird_10,
            name = "Robin Redbreast",
            location = "Europe",
            condition1 = "wingspan 7.5-8.9 inches",
            condition2 = "",
            details = "The European robin, known simply as the robin or robin redbreast in Great Britain, is a small insectivorous passerine bird that belongs to the chat subfamily of the Old World flycatcher family.",
        ),
        Image(
            id = 23,
            image = R.drawable.bird_11,
            name = "North Island Brown Kiwi",
            location = "New Zealand",
            condition1 = "",
            condition2 = "has tiny wings, but cannot fly",
            details = "The North Island brown kiwi, is a species of kiwi that is widespread in the northern two-thirds of the North Island of New Zealand and, with about 35,000 remaining, is the most common kiwi. It holds the world record for laying the largest eggs relative to its body size.",
        ),
        Image(
            id = 24,
            image = R.drawable.bird_12,
            name = "Osprey",
            location = "Africa, Americas",
            condition1 = "wingspan 152-167 centimeters",
            condition2 = "",
            details = "The osprey or more specifically the western osprey — also called sea hawk, river hawk, and fish hawk — is a diurnal, fish-eating bird of prey with a cosmopolitan range. It is a large raptor, reaching more than 60 cm in length and 180 cm across the wings.",
        ),
        Image(
            id = 25,
            image = R.drawable.animal_1,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 26,
            image = R.drawable.animal_2,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 27,
            image = R.drawable.animal_3,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 28,
            image = R.drawable.animal_4,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 29,
            image = R.drawable.animal_5,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 30,
            image = R.drawable.animal_6,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 31,
            image = R.drawable.animal_7,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 32,
            image = R.drawable.animal_8,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 33,
            image = R.drawable.animal_9,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 34,
            image = R.drawable.animal_10,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 35,
            image = R.drawable.animal_11,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 36,
            image = R.drawable.animal_12,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 37,
            image = R.drawable.lake_1,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 38,
            image = R.drawable.lake_2,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 39,
            image = R.drawable.lake_3,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 40,
            image = R.drawable.lake_4,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 41,
            image = R.drawable.lake_5,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 42,
            image = R.drawable.lake_6,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 43,
            image = R.drawable.lake_7,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 44,
            image = R.drawable.lake_8,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 45,
            image = R.drawable.lake_9,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 46,
            image = R.drawable.lake_10,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 47,
            image = R.drawable.lake_11,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 48,
            image = R.drawable.lake_12,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 49,
            image = R.drawable.rock_1,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 50,
            image = R.drawable.rock_2,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 51,
            image = R.drawable.rock_3,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 52,
            image = R.drawable.rock_4,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 53,
            image = R.drawable.rock_5,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 54,
            image = R.drawable.rock_6,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 55,
            image = R.drawable.rock_7,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 56,
            image = R.drawable.rock_8,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 57,
            image = R.drawable.rock_9,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 58,
            image = R.drawable.rock_10,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 59,
            image = R.drawable.rock_11,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
        Image(
            id = 60,
            image = R.drawable.rock_12,
            name = "Description",
            location = "Southern Asia",
            condition1 = "medium indirect filtered light",
            condition2 = "1.3 metres high",
            details = "Homalomena rubescens is usually seen in southern parts of Asia. It enjoys damp, humid environment of the rainforest floor, receiving medium indirect filtered light through the canopy. In their best conditions they reach about a height around 1.3 meters to 1.5 meters.",
        ),
    )

    val plantList = images.subList(0, 12)
    val birdList = images.subList(12, 24)
    val animalList = images.subList(24, 36)
    val lakeList = images.subList(36, 48)
    val rockList = images.subList(48, 60)

    fun getImage(imageId: Int) = images.find { it.id == imageId }
}
