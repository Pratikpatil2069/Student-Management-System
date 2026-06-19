import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

function EditStudent() {
  const [name, setName] = useState("");
  const [age, setAge] = useState("");
  const [email, setEmail] = useState("");

  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    fetch(`http://localhost:8080/Student/getById/${id}`)
      .then((response) => response.json())
      .then((data) => {
        setName(data.name);
        setAge(data.age);
        setEmail(data.email);
      });
  }, [id]);

  function updateStudent() {
    const student = {
      name: name,
      age: age,
      email: email,
    };

    fetch(`http://localhost:8080/Student/update/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(student),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        navigate("/students");
      });
  }

  return (
    <div className="max-w-3xl mx-auto p-6">
      <div className="bg-white p-6 rounded-xl shadow-md">
        <h2 className="text-3xl font-bold text-center mb-6">Edit Student</h2>

        <div className="space-y-4">
          <div>
            <label className="block font-medium mb-2">Name</label>

            <input
              type="text"
              value={name}
              onChange={(event) => setName(event.target.value)}
              className="w-full border rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block font-medium mb-2">Age</label>

            <input
              type="number"
              value={age}
              onChange={(event) => setAge(event.target.value)}
              className="w-full border rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block font-medium mb-2">Email</label>

            <input
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              className="w-full border rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <button
            onClick={updateStudent}
            className="w-full bg-green-600 text-white p-3 rounded-lg hover:bg-green-700 transition"
          >
            Update Student
          </button>
        </div>
      </div>
    </div>
  );
}

export default EditStudent;
