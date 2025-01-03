# Slide.kt

`Slide.kt` is an application to make presentation slide on Compose Multiplatform using Kotlin DSL.

## Requirement

JetbrainsRuntime JDK 21.
It will be automatically downloaded when running this app.

## Usage

1. [Fork this repo](https://github.com/T45K/Slide.kt/fork)
2. Rewrite [`main.kt`](./src/main/sample/main.kt) as you like

## DSL

A presentation is composed of 0 or 1 `cover` and 0 or more `slide`.

`Cover` has the following elements.

|         Name         | Description                                                         |          Required           |
|:--------------------:|:--------------------------------------------------------------------|:---------------------------:|
|       `title`        | Title of the presentation                                           |             Yes             |
|        `name`        | Your name                                                           |             Yes             |
| `horizontalPosition` | Elements position in the cover                                      |    No (default: CENTER)     |
|        `date`        | Date when the presentation is given. Given by `java.time.LocalDate` | No (default: Not displayed) |
|       `event`        | Event where the presentation is given (e.g., `XXX conference`)      | No (default: Not displayed) |
|      `location`      | where the presentation is given (e.g., `YYY buildings`)             | No (default: Not displayed) |

`Slide` has the following elements.

|    Name     | Description                                                                                                                |          Required           |
|:-----------:|:---------------------------------------------------------------------------------------------------------------------------|:---------------------------:|
|   `title`   | Title of the slide                                                                                                         | No (default: Not displayed) |
|  `textBox`  | Text box. You can use simple `sentence`, `itemize`, or `numbering`. You can use `bold` and `color` modification in a text. | No (default: Not displayed) |
| `imagePath` | Image path under [`src/main/resources`](./src/main/resources) directory                                                    | No (default: Not displayed) |
|   `code`    | Source code. Kotlin code highlight will be used                                                                            | No (default: Not displayed) |

You can use either `textBox`, `imagePath`, or `code` in a single slide (due to space limitation).

For more details. please refer to [sample code](./src/main/sample/main.kt).

## Special Thanks

This application is inspired by [SlideKit](https://github.com/mtj0928/SlideKit), which is an application to make
presentation slides on SwiftUI. 
