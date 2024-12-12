// Initialize Quill editor
const quill = new Quill("#editor-container", {
    theme: "snow",
    modules: {
        toolbar: [
            [{ header: "1" }, { header: "2" }, { font: [] }],
            [{ list: "ordered" }, { list: "bullet" }],
            ["bold", "italic", "underline", "strike"],
            ["link"],
            [{ align: [] }],
            ["image"],
            ["blockquote", "code-block"],
            [{ color: [] }, { background: [] }],
            [{ script: "sub" }, { script: "super" }],
            [{ direction: "rtl" }],
            ["clean"]
        ]
    }
});

// Save document function
function saveDocument() {
    const docContent = quill.root.innerHTML; // Get the content from Quill editor
    localStorage.setItem("savedDocument", docContent); // Save it to localStorage
    alert("Document saved!");
}

// Load document function
function loadDocument() {
    const savedContent = localStorage.getItem("savedDocument"); // Retrieve saved content from localStorage
    if (savedContent) {
        quill.root.innerHTML = savedContent; // Load the saved content into the Quill editor
    } else {
        alert("No document found.");
    }
}

// Function to handle sending prompt to the ChatGPT API
async function sendChatPrompt() {
    const prompt = document.getElementById("chatPrompt").value;

    if (!prompt.trim()) {
        alert("Prompt cannot be empty!");
        return;
    }

    try {
        const response = await fetch("/api/chat/response", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ prompt }),
        });

        if (!response.ok) {
            throw new Error(`Error ${response.status}: ${response.statusText}`);
        }

        // Get response text and insert it into Quill editor
        const chatResponse = await response.text();
        quill.setText(chatResponse);  // Insert the response into the editor

        // Optionally, append response instead of replacing all content
        // quill.setText(quill.getText() + chatResponse);

        // Clear the prompt input field
        document.getElementById("chatPrompt").value = '';
    } catch (error) {
        console.error("Chat Error:", error);
        alert("Error getting AI response: " + error.message);
    }
}

// Attach event listeners
document.getElementById("saveButton").addEventListener("click", saveDocument);
document.getElementById("loadButton").addEventListener("click", loadDocument);
document.getElementById("sendChatButton").addEventListener("click", sendChatPrompt);
