package ru.ildar.services;

import org.springframework.stereotype.Service;

@Service
public interface PictureService
{
    /**
     * Returns an avatar of the specified user, whether it's a
     * teacher or a student.
     */
    byte[] getPicture(String username);

    /**
     * Set the avatar specified by the array of bytes to the
     * person specified by his username, whether it's a teacher or
     * a student
     * @param bytes An avatar image
     * @param name Username of a person
     */
    void setPhoto(byte[] bytes, String name);

    /**
     * Remove the avatar from a user
     * @param username Username of the user
     */
    void removeAvatar(String username);
}
