package com.catshome.classJournal.ui

//
//@Composable
//fun Dialog(onConfirmation:()->Unit, onDismissRequest: () -> Unit) {
//    val context = LocalContext.current
//    AlertDialog(
//        icon = {
//            Icon(Icons.Default.Warning, contentDescription = "Example Icon")
//        },
//        title = {
//            Text(text = context.getString(R.string.delete_Title))
//        },
//        text = {
//            Text(text = context.getString(R.string.delete_message))
//        },
//        onDismissRequest = {
//            onDismissRequest()
//        },
//        confirmButton = {
//            TextButton(
//                onClick = {
//                    onConfirmation()
//                }
//            ) {
//                Text(context.getString(R.string.bottom_delete))
//            }
//        },
//        dismissButton = {
//            TextButton(
//                onClick = {
//                    onDismissRequest()
//                }
//            ) {
//                Text(context.getString(R.string.bottom_cancel))
//            }
//        }
//    )
//}

//AlertDialog(onDismissRequest = { onDismissRequest() }) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
//                .padding(24.dp),
//            shape = RoundedCornerShape(ClassJournalTheme.shapes.padding)
//        )
//        {
//            Column(
//                Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(top = 24.dp, bottom = 56.dp)
//                ) {
//                    Icon(
//                        Icons.Default.Delete,
//                        modifier = Modifier.padding(start = 16.dp),
//                        tint = ClassJournalTheme.colors.errorColor,
//                        contentDescription = ""
//                    )
//                    Text(text = context.getString(R.string.delete_message),
//                        style = ClassJournalTheme.typography.heading,
//                        modifier = Modifier.padding(start = 8.dp).fillMaxWidth(),
//
//                        )
//                }
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center,
//                ) {
//                    Button(
//                        modifier = Modifier
//                            .width(120.dp)
//                            .height(56.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = ClassJournalTheme.colors.controlColor,
//                            contentColor = ClassJournalTheme.colors.primaryText
//                        ),
//                        onClick = onDismissRequest
//                    ) {
//                        Row(Modifier.fillMaxWidth()) {
//                            Icon(
//                                Icons.Default.Delete,
//                                contentDescription = "",
//                                Modifier.padding(end = 8.dp),
//                                tint = ClassJournalTheme.colors.errorColor
//                            )
//                            Text(context.getString(R.string.bottom_delete))
//                        }
//                    }
//                    Button(
//                        modifier = Modifier
//                            .width(130.dp)
//                            .padding(start = 16.dp)
//                            .height(56.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = ClassJournalTheme.colors.controlColor,
//                            contentColor = ClassJournalTheme.colors.primaryText
//                        ),
//                        onClick = onCancel
//                    ) {
//                        Text(context.getString(R.string.bottom_cancel))
//                    }
//                }
//            }
//        }
//    }
//}
