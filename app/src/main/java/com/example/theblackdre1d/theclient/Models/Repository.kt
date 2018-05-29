package com.example.theblackdre1d.theclient.Models

import io.realm.RealmList
import io.realm.RealmObject

/*
* Data class for store information about repository.
* */
data class Repository(var name:         String,
                      var description:  String,
                      var language:     String,
                      var account:      String,
                      var favorite:     Boolean?                        = false,
                      var commits:      RealmList<GitHubCommit>?        = null,
                      var pulls:        RealmList<GitHubPullRequest>?   = null,
                      var readmeText:   String?                         = null) /*: RealmObject() {
    constructor() : this(
            "",
            "",
            "",
            "",
            false,
            RealmList<GitHubCommit>(),
            RealmList<GitHubPullRequest>(),
            ""
    )
}
*/
