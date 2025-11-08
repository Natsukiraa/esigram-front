package com.example.esigram.repositories

import com.example.esigram.models.Conversation
import com.example.esigram.models.Story
import com.example.esigram.models.User

class StoryRepository {

    var stories = mutableListOf<Story>(
        Story(
            "1",
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        ),
        Story(
            "1",
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        ),
        Story(
            "1",
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        ),
        Story(
            "1",
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        ),
        Story(
            "1",
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        ),
        Story(
            "1",
            User(
                id = "1",
                forename = "Arthur",
                name = "Morelon",
                image = "https://randomuser.me/api/portraits/men/1.jpg",
                isOnline = true
            )
        ),

        )

    fun getAll(): List<Story> = stories

    fun getById(id: String): Story? = stories.find{ it.id == id }

    fun addNote(story: Story) = stories.add(story)

    fun deleteNote(story: Story) = stories.removeIf { it.id == story.id }
}