package uz.itteacher.itschoolbank.service

import com.google.firebase.database.FirebaseDatabase

class FirebaseService {
    private val db = FirebaseDatabase.getInstance().reference
    private val notificationsRef = db.child("notifications")

    /**
     * Store a simple notification record. A server-side function (Cloud Function) may forward to FCM.
     */
    fun sendNotification(toUserId: String, title: String, message: String) {
        val id = notificationsRef.push().key ?: return
        val payload = mapOf(
            "id" to id,
            "toUserId" to toUserId,
            "title" to title,
            "message" to message,
            "timestamp" to System.currentTimeMillis()
        )
        notificationsRef.child(id).setValue(payload)
    }
}
