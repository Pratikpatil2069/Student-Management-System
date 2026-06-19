import { useState } from "react";
import { useNavigate } from "react-router-dom";

function AddStudent() {
  const [name, setName] = useState("");
  const [age, setAge] = useState("");
  const [email, setEmail] = useState("");

  const navigate = useNavigate();

  function addStudent() {
    const student = {
      name: name,
      age: age,
      email: email,
    };

    fetch("http://localhost:8080/Student/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(student),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        setName("");
        setAge("");
        setEmail("");

        navigate("/students");
      });
  }

  return (
    <div className="max-w-3xl mx-auto p-6">
      <div className="bg-white p-6 rounded-xl shadow-md">
        <h2 className="text-3xl font-bold mb-6 text-center">Add Student</h2>

        <div className="space-y-4">
          <div>
            <label className="block font-medium mb-2">Name</label>

            <input
              type="text"
              placeholder="Enter the name"
              value={name}
              onChange={(event) => setName(event.target.value)}
              className="w-full border rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block font-medium mb-2">Age</label>

            <input
              type="number"
              placeholder="Enter the age"
              value={age}
              onChange={(event) => setAge(event.target.value)}
              className="w-full border rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block font-medium mb-2">Email</label>

            <input
              type="email"
              placeholder="Enter the email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              className="w-full border rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <button
            onClick={addStudent}
            className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 hover:scale-[1.01] transition duration-300"
          >
            Add Student
          </button>
        </div>
      </div>
    </div>
  );
}

export default AddStudent;
